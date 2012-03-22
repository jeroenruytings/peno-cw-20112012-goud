package pacmansystem.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PathLayer;
import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;

import data.board.Board;
import data.board.Panel;
import data.board.PointConvertor;
import data.board.operations.BoardUnifier;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.enums.Orientation;
import data.lazy.TransformedRobotData;
import data.transformed.Transformation;
import data.world.OwnRobotData;
import data.world.RobotData;
import data.world.RobotDataView;
import data.world.World;

public class RobotController
{

	private PathLayer pathLayer;
	private World world;

	private Map<RobotData, PointConvertor> convertors = new HashMap<RobotData, PointConvertor>();
	private Map<RobotData,RobotData> otherRobots=new HashMap<RobotData,RobotData>();
	private String name_;
	
	public RobotData getData()
	{
		return world.getRobot(getName());
	}
	private OwnRobotData getOwnData()
	{
		return (OwnRobotData) world.getRobot(getName());
	}

	public RobotController(OrientationLayer layer, String name, World aWorld){
		OwnRobotData data = new OwnRobotData(name);//TODO:make this a fancy robotdata !
		this.world=aWorld;
		this.world.setRobot(data,name);
		this.name_=name;
		pathLayer = new PathLayer(data, layer);
	}
	
	public Map<RobotData, PointConvertor> getConvertors()
	{
		return convertors;
	}

	public PathLayer getPathLayer()
	{
		return pathLayer;
	}
	
	public World getWorld(){
		return world;
	}

	public void explore()
	{
		Point destination = null;
		getOwnData().position(new Point(0,0));
		while (true) {
			//checkForNewInfo();
			// voegt panel toe aan board
			tryAddingOtherRobots();
			Panel p1 = getPathLayer().getPanel(getCurrentOrientation());
			boolean pacmanSpotted = getPathLayer().getOrientationLayer().getLayer().getPacman();
			if(pacmanSpotted)
				getData().setPacman(getCurrentPoint());
			// kijken
			
			try {
				getBoard().add(p1, getCurrentPoint());
			} catch (IllegalArgumentException e) {
				getBoard().addForced(p1, getCurrentPoint());
			}
			// voegt panel toe aan board
		
			if (p1.hasBarcode()){
				getOwnData().barcode(p1.getBarcode(), p1.getBarcodeOrientation(), getCurrentPoint());
			}

			for(RobotDataView data: world.get_robots().values())
			{
				Board newBoard = BoardUnifier.unify2(getBoard(), data.getBoard());
				for(Point point: newBoard.getFilledPoints())
				{	
					if(!getBoard().hasPanelAt(point)){
						getOwnData().discover(point, newBoard.getPanelAt(point));
						if(newBoard.getPanelAt(point).hasBarcode())
						{
							getOwnData().barcode(newBoard.getPanelAt(point).getBarcode(), newBoard.getPanelAt(point).getBarcodeOrientation(), point);
						}
						if(data.getPacmanLastSighted()!=null){
							Transformation convertToThisRobot = new Transformation(this.getData(), data);
							TransformedRobotData transRobot = new TransformedRobotData(convertToThisRobot, data);
							getOwnData().pacman(transRobot.getPacmanLastSighted());
						}
							
					}
				}
			}

			destination = lookForDestination(); // zoekt volgend punt om naartoe
			// te gaan
			if (destination == null) {
				driveToPacman();
				return;
			}
			try {
				getPathLayer().go(getCurrentPoint(), destination);
			} catch (IllegalDriveException e) {
				e.printStackTrace();
			} // gaat naar volgend punt

		}
		
	}
	
	private void driveToPacman() {
		if(getData().getPacmanLastSighted() != null){
			try {
				getPathLayer().go(getCurrentPoint(), getData().getPacmanLastSighted());
			} catch (IllegalDriveException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("Geen pacman gevonden!");
		}
	}
	
	public Orientation getCurrentOrientation()
	{
		return getPathLayer().getOrientationLayer().getOrientation();
	}
	
	public Point lookForDestination()
	{
		//		return ssMostUnknown();
		//		 return ssClosestPoint();
		Point destination = null;
		Orientation orientation = nextMove();
		if (orientation == null)
			destination = searchNext();
		else
			destination = new Point(getCurrentPoint().x+ orientation.getXPlus(),
					getCurrentPoint().y + orientation.getYPlus());
		return destination;

	}

	private void tryAddingOtherRobots()
	{
		synchronized (world.get_robots()) {
		for(RobotData data:world.get_robots().values())
		{
			if(!data.getName().equals(this.getName()))
			{
				if(Transformation.canBeBuild(this.getData(), data))
				{
					if(!otherRobots.containsKey(data))
						otherRobots.put(data, new TransformedRobotData(new Transformation(this.getData(), data), data));
				}
			}
		}
		}
	}

	private Board getBoard()
	{
		return getData().getBoard();
	}

	private Point getCurrentPoint()
	{
		return getData().getPosition();
	}

	private Point ssMostKnown()
	{
		Point shortest = null;
		int max = 0;
		for (Point point : getBoard().getUnfilledPoints()) {
			int c = 1;
			for (Point p : getBoard().getSurrounding(point)) {
				if (getBoard().hasPanelAt(p))
					c++;
			}
			//c = c - dist(getCurrentPoint(), point);
			if (c > max) {
				shortest = point;
				max = c;
			}
		}
		return shortest;
	}
	
	private Point ssMostUnknown()
	{
		Point shortest = null;
		int max = -1000;
		for (Point point : getBoard().getUnfilledPoints()) {
			int c = -1;
			for (Point p : getBoard().getSurrounding(point)) {
				if (!getBoard().hasPanelAt(p))
					c++;
			}
			c = c - dist(getCurrentPoint(), point);
			if (c > max) {
				shortest = point;
				max = c;
			}
		}
		return shortest;
	}

	private int dist(Point position, Point point)
	{
		return new DijkstraFinder(getBoard()).shortestPath(position, point)
				.size();
	}

	private Point ssClosestPoint()
	{
		Point shortest = null;
		int min = 10000;
		DijkstraFinder f = new DijkstraFinder(getBoard());
		for (Point point : getBoard().getUnfilledPoints()) {
			List<Point> path = f.shortestPath(getCurrentPoint(), point);
			if (min > path.size()) {
				min = path.size();
				shortest = point;
			}
		}
		return shortest;
	}

	/**
	 * 
	 * @return de ori�ntatie waar je naartoe moet.
	 * @return null als alle omliggende vakjes gekend zijn.
	 */
	private Orientation nextMove()
	{
		Point position = getCurrentPoint();
		Orientation best = null;
		int nbUnknowns = 0;
		for (Orientation orientation : Orientation.values()) {
			if (getBoard().wallBetween(position, orientation))
				continue;
			if (getBoard().hasPanelAt(orientation.addTo(getCurrentPoint())))
				continue;
			Point possibleDest = orientation.addTo(getCurrentPoint());
			int temp = getBoard().nbOfUnknowns(possibleDest);
			if (temp >= nbUnknowns) {
				best = orientation;
				nbUnknowns = temp;
			}
		}
		return best;
	}

	/**
	 * 
	 * @return het beste punt om naar toe te rijden
	 * @return null als alle punten gekend zijn.
	 */
	private Point searchNext()
	{
		Point best = null;
		int waarde = 1000;
		for (Point point : getBoard().getPanels().keySet()) {
			if (point.equals(getCurrentPoint()))
				continue;
			int nbKnown = 4 - getBoard().nbOfUnknowns(point);
			if (nbKnown == 4)
				continue;
			int temp = 3*nbKnown + heuristiek(point);
			if (temp < waarde) {
				best = point;
				waarde = temp;
			}
		}
		return best;
	}

	private int heuristiek(Point destination)
	{
		return (int) (Math.abs(destination.getX() - getCurrentPoint().x) + Math
				.abs(destination.getY() - getCurrentPoint().y));
	}

	public void establishConnection()
	{
		world.start(getData());
	}
	public void start()
	{
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				
				explore();
				
			}
		}).start();
	}

	public String getName()
	{
		return name_;
	}

    public Map<RobotData, Point> getRobotsWithSameBarcode(Barcode barcode)
    {
		if (barcode == null)
			return null;
		Map<RobotData, Point> robots = new HashMap<RobotData, Point>();
		for (RobotData robot : world.get_robots().values()) {
			if(robot.getName().equals(getData().getName()))
				continue;
			for(Barcode code : robot.getBarcodes().keySet()){
				if(code.equals(barcode))
					robots.put(robot, robot.getBarcodes().get(code));
			}
		}
		return robots;
	}

	public Map<Orientation, Orientation> getRelativeOrientationAfterBarcode(
			RobotDataView firstRobot, RobotDataView secondRobot,
			Point barcodePointFirstRobot, Point barcodePointSecondRobot)
	{
		Panel p1 = firstRobot.getBoard().getPanelAt(barcodePointFirstRobot);
		Panel p2 = secondRobot.getBoard().getPanelAt(barcodePointSecondRobot);
		Orientation orientationBarcodeFirstPanel = p1.getBarcodeOrientation();
		Orientation orientationBarcodeSecondPanel;
		if(p1.getBarcode().getReverse() == p2.getBarcode().getValue()){
			orientationBarcodeSecondPanel = p2.getBarcodeOrientation();
		}
		else{
			orientationBarcodeSecondPanel = p2.getBarcodeOrientation().opposite();
		}
		Map<Orientation, Orientation> orientations = new HashMap<Orientation, Orientation>();
		int ordinalFirst = orientationBarcodeFirstPanel.ordinal();
		int ordinalSecond = orientationBarcodeSecondPanel.ordinal();
		orientations.put(orientationBarcodeFirstPanel,
				orientationBarcodeSecondPanel);
		orientations.put(orientationBarcodeFirstPanel.opposite(),
				orientationBarcodeSecondPanel.opposite());
		ordinalFirst = (ordinalFirst + 1) % 4;
		ordinalSecond = (ordinalSecond + 1) % 4;
		orientationBarcodeFirstPanel = Orientation.fromOrdinal(ordinalFirst);
		orientationBarcodeSecondPanel = Orientation.fromOrdinal(ordinalSecond);
		orientations.put(orientationBarcodeFirstPanel,
				orientationBarcodeSecondPanel);
		orientations.put(orientationBarcodeFirstPanel.opposite(),
				orientationBarcodeSecondPanel.opposite());
		return orientations;
	}

	public Collection<RobotData> getOtherBots()
	{
		return new ArrayList<RobotData>(otherRobots.values());
	}
}
