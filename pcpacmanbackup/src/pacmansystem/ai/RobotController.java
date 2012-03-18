package pacmansystem.ai;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PathLayer;
import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import util.board.Board;
import util.board.Panel;
import util.board.PointConvertor;
import util.board.operations.BoardUnifier;
import util.board.shortestpathfinder.dijkstra.DijkstraFinder;
import util.enums.Orientation;
import util.lazy.TransformedRobotData;
import util.transformed.Transformation;
import util.world.RobotData;
import util.world.RobotDataView;
import util.world.World;

import communicator.be.kuleuven.cs.peno.MessageSender;

public class RobotController
{

	private RobotData data = new RobotData();

	public RobotData getData()
	{
		return data;
	}

	private PathLayer pathLayer;
	private World world;

	private Map<RobotData, PointConvertor> convertors = new HashMap<RobotData, PointConvertor>();
	private Map<RobotData,RobotData> otherRobots=new HashMap<RobotData,RobotData>();
	
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

	public void join()
	{
		try {
			MessageSender.getInstance().sendMessage("JOIN\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void sendName(){
		try {
			MessageSender.getInstance().sendMessage(getName() + " NAME 1.0\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void explore()
	{
		Point destination = null;
		while (true) {
			checkForNewInfo();
			// voegt panel toe aan board
			tryAddingOtherRobots();
			Panel p1 = getPathLayer().getOrientationLayer().getPanel(
					getCurrentOrientation()); // getPanel() moet om zich heen
			// kijken

			try {
				getBoard().add(p1, getCurrentPoint());
			} catch (Exception e) {
				getBoard().addForced(p1, getCurrentPoint());
			}
			// voegt panel toe aan board
			try {
				MessageSender.getInstance().sendMessage(
						getName()
						+ " DISCOVER "
						+ pointToString(getCurrentPoint())
						+ ""
						+ getBoard().getPanelAt(getCurrentPoint())
						.bordersToString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (p1.hasBarcode()){
//				getData().getBarcodes().put(p1.getBarcode(), getCurrentPoint());
				sendBarcode(p1.getBarcode(),p1.getBarcodeOrientation());

//				Map<RobotData, Point> robots = getRobotsWithSameBarcode(p1.getBarcode());
//				if(robots!=null){
//					for(RobotData robot : robots.keySet()){
//						if(!getConvertors().keySet().contains(robots))
//							mergeBoard(robot,robots.get(robot));
//					}
//				}
			}

			for(RobotDataView data: world.get_robots().values())
			{
				Board newBoard = BoardUnifier.unify2(getBoard(), data.getBoard());
				for(Point point: newBoard.getFilledPoints())
				{	
					if(!getBoard().hasPanelAt(point))
						System.out.println("tst" );
					getBoard().add(newBoard.getPanelAt(point), point);
				}
			}

			
			 

			// for (Orientation orientation : Orientation.values()) { //voegt
			// omliggende punten toe indien ze geen tussenmuur hebben
			// if(p1.hasBorder(orientation)){
			// Point location = new
			// Point(getCurrentX()+orientation.getXPlus(),getCurrentY()+orientation.getYPlus());
			// Panel p2;
			// if (getBoard().getPanelAt(location) == null){
			// p2 = new Panel();
			// getBoard().add(p2, location);
			// }
			// else
			// p2 = getBoard().getPanelAt(location);
			// p2.setBorder(orientation.opposite(), true);
			// }
			// }
			destination = lookForDestination(); // zoekt volgend punt om naartoe
												// te gaan
			if (destination == null)
				return;
			try {
				getPathLayer().go(getCurrentPoint(), destination);
			} catch (IllegalDriveException e) {
				e.printStackTrace();
			} // gaat naar volgend punt
				// setCurrentOrientation(Board.getOrientationBetween(getCurrentPoint(),
				// destination)); //verandert orientatietry {
			
			}
	}
	
	private void tryAddingOtherRobots()
	{
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

	private String pointToString(Point p)
	{
		return p.x + "," + p.y;
	}

	

	private void checkForNewInfo()
	{
		for (RobotDataView robot : getConvertors().keySet()) {
			for (Point pointFromBoard : robot.getBoard().getPanels().keySet()) {
				Point pointBoard = getConvertors().get(robot).convert(
						pointFromBoard);
				if (getBoard().getPanelAt(pointBoard) == null) {
					try {
						Panel p = robot.getBoard().getPanelAt(pointFromBoard);
						Panel q = new Panel();
						if (p.hasBarcode()) {
							q.setBarcode(p.getBarcode());
							q.setBarcodeOrientation(getConvertors()
									.get(robot).getOrientationsInverse().get(p.getBarcodeOrientation()));

						}
						for (Orientation orientation : getConvertors()
								.get(robot).getOrientationsInverse().keySet()) {
							q.setBorder(
									orientation,
									p.hasBorder(getConvertors().get(robot)
											.getOrientationsInverse().get(orientation)));
						}
						getBoard().add(q, pointBoard);
					} catch (Exception e) {
						getConvertors().remove(robot);
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void mergeBoard(RobotData robot, Point point)

	{
		
		HashMap<Orientation, Orientation> orientations = (HashMap<Orientation, Orientation>) getRelativeOrientationAfterBarcode(
				getData(), robot, getCurrentPoint(), point);
		PointConvertor convertor = new PointConvertor(getCurrentPoint(), point,
				orientations);
		getConvertors().put(robot, convertor);
		Map<Orientation, Orientation> orientationsInverse = convertor.getOrientationsInverse();
		for (Point pointFromBoard : robot.getBoard().getPanels().keySet()) {
			Point pointBoard = convertor.convert(pointFromBoard);
			if (!getBoard().hasPanelAt(pointBoard)) {
				try {
					Panel p = robot.getBoard().getPanelAt(pointFromBoard);
					Panel q = new Panel();
					if (p.hasBarcode()) {
						q.setBarcode(p.getBarcode());
						q.setBarcodeOrientation(orientationsInverse.get(p.getBarcodeOrientation()));
					}
					for (Orientation orientation : orientationsInverse.keySet()) {
						q.setBorder(orientation,
								p.hasBorder(orientationsInverse.get(orientation)));
					}
					getBoard().add(q, pointBoard);
				} catch (Exception e) {
					// paneel geeft conflict => niet toevoegen
					getConvertors().remove(robot);
					e.printStackTrace();
				}
			}
		}


    }

	private Board getBoard()
	{
		return data.getBoard();
	}

	private void sendBarcode(Barcode barcode,Orientation o)
	{
		try {
			MessageSender.getInstance().sendMessage(
					getName() + " BARCODE " + barcode.getValue() + " "
							+ orientationToString(o) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String orientationToString(Orientation o){
		switch (o) {
		case NORTH:
			return "1";
		case SOUTH:
			return "3";
		case WEST:
			return "4";
		case EAST:
			return "2";

		}
		return null;
	}

	public Orientation getCurrentOrientation()
	{

		return getPathLayer().getOrientationLayer().getOrientation();
	}

	private Point getCurrentPoint()
	{
		return new Point(getCurrentX(), getCurrentY());
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
			destination = new Point(getCurrentX() + orientation.getXPlus(),
					getCurrentY() + orientation.getYPlus());
		 return destination;

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
			Point possibleDest = new Point(getCurrentX()
					+ orientation.getXPlus(), getCurrentY()
					+ orientation.getYPlus());
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
			int temp = nbKnown + heuristiek(point);
			if (temp < waarde) {
				best = point;
				waarde = temp;
			}
		}
		return best;
	}

	private int heuristiek(Point destination)
	{
		return (int) (Math.abs(destination.getX() - getCurrentX()) + Math
				.abs(destination.getY() - getCurrentY()));
	}

	public RobotController(OrientationLayer layer)
	{
		this(layer,false, "Goud" + Math.random());
	}
	
	public RobotController(OrientationLayer layer, boolean hasMessageReceiver, String name){
		initWorld();
		data.setName(name);
		pathLayer = new PathLayer(getData(), layer);
	}
	
	public RobotController(Board b, OrientationLayer layer)
	{
		world = new World();
		data = new RobotData(b);
		//world.addRobot(data, "Goud");
		// currentOrientation = Orientation.NORTH;
		pathLayer = new PathLayer(getData(), layer);
	}

	public int getCurrentX()
	{
		return data.getPosition().x;
	}

	public void setCurrentX(int x)
	{
		data.getPosition().setLocation(x, getCurrentY());
	}

	public int getCurrentY()
	{
		return data.getPosition().y;
	}

	public void setCurrentY(int y)
	{
		data.getPosition().setLocation(getCurrentX(), y);
	}

	public String getName()
	{
		return data.getName();
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


	
	private void initWorld()
	{
		data = new RobotData();
		world = new World();
		//world.addRobot(getData(), getData().getName());
	}

	public Point getLocation()
	{
		return new Point(getCurrentX(), getCurrentY());
	}
	
	public Collection<RobotData> getOtherBots()
	{
		return new ArrayList<RobotData>(otherRobots.values());
	}
}
