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
import pacmansystem.ai.robot.fysicalRobot.connector.CrashedException;
import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import pacmansystem.ai.strategy.Explore;
import pacmansystem.ai.strategy.Strategy;
import data.board.Board;
import data.board.Panel;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;
import data.enums.Direction;
import data.enums.Orientation;
import data.lazy.TransformedRobotData;
import data.transformed.Transformation;
import data.world.OwnRobotData;
import data.world.RobotData;
import data.world.RobotDataView;
import data.world.World;

public class RobotController
{

	private Strategy strategy;
	private PathLayer pathLayer;
	private World world;

	private Map<RobotData, RobotData> otherRobots = new HashMap<RobotData, RobotData>();
	private String name_;

	public RobotController(OrientationLayer layer, String name, World aWorld)
	{
		OwnRobotData data = new OwnRobotData(name);
		this.world = aWorld;
		this.world.setRobot(data, name);
		this.name_ = name;
		strategy = new Explore(this);
		pathLayer = new PathLayer(data, layer);
	}

	public void establishConnection()
	{
		world.start(getOwnData());
	}

	public void drive()
	{
		Point destination = null;
		List<Point> plan = null;
		getOwnData().position(new Point(0, 0));		
		while (!strategy.hasCaughtPacman()) {
			if (strategy.hasToSwitchStrategy())
				switchStrategy(strategy.getReplacingStrategy());
			addPanel();
			fixInfoFromOtherRobots();
			addPacman();
			plan = strategy.constructRoute();
			getOwnData().plan(plan);
			while (getOwnData().getRemainingPlan().size() > 0) {
				if (!strategy.hasFinishedExploring()) {
					addPanel();
					fixInfoFromOtherRobots();
				}
				addPacman();
				
				if (strategy.hasToSwitchStrategy()) {
					switchStrategy(strategy.getReplacingStrategy());
					getOwnData().cancelPlan();
					break;
				}
				
				if (strategy.hasToUpdatePlan()) {
					getOwnData().cancelPlan();
					break;
				}
				
				destination = getOwnData().getRemainingPlan().get(0); // zoekt volgend punt om naartoe te
																		// gaan
				System.out.println("riding from " + getCurrentPoint() + " to "+destination);
				try {
					getPathLayer().goOneStep(getCurrentPoint(), destination);
				} catch (IllegalDriveException e) {
					e.printStackTrace();
				}catch (CrashedException e) {
					getOwnData().getBoard().getPanels().remove(getCurrentPoint());
				}
			}
		}
		//pacman has been caught

	}
	
	private void switchStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	private void addPacman() {
		for (RobotDataView robot : getOtherBots()) {
			if (robot.getPacmanLastSighted() == null)
				continue;
			if (getOwnData().getPacmanLastSighted() != null
					&& robot.getPacmanLastSighted().equals(
							getOwnData().getPacmanLastSighted()))
				continue;
			if (robot.getPacmanLastSighted() == null
					|| !robot.getPacmanLastSighted().equals(
							getOwnData().getPacmanLastSighted())) {
				getOwnData().pacman(robot.getPacmanLastSighted());
			}
		}
	}
	
	private void addPanel() {
		// voegt panel toe aan board
		tryAddingOtherRobots();
		Panel p1 = getPathLayer().getPanel();
		Direction pacmanSpotted = getPathLayer().getOrientationLayer()
				.getLayer().getPacmanDirection();
		if (pacmanSpotted != null) {
			Point pacmanLocation = getOwnData().getOrientation()
					.addTo(pacmanSpotted).addTo(getCurrentPoint());
			getOwnData().pacman(pacmanLocation);
			if (!this.getMergedBoard().hasPanelAt(pacmanLocation)) {
				Panel pacmanPanel = new Panel();

				this.getOwnData().discover(pacmanLocation, pacmanPanel);
			}
		}
		if (p1.hasBarcode()) {
			getOwnData().barcode(p1.getBarcode(), p1.getBarcodeOrientation(),
					getCurrentPoint());
		}
		// kijken
		try {
			addToBothBoards(p1, getCurrentPoint());
		} catch (IllegalArgumentException e) {
			addForcedToBothBoards(p1, getCurrentPoint());
		} // voegt panel toe aan board
	}

	private void fixInfoFromOtherRobots()
	{
		for (RobotDataView robot : getOtherBots())
			for (Point p : robot.getBoard().getFilledPoints())
				if (!this.getMergedBoard().hasPanelAt(p)) {
					try {
						this.getMergedBoard().add(robot.getBoard().getPanelAt(p), p);
					} catch (IllegalArgumentException e) {
						// getBoard().addForced(robot.getBoard().getPanelAt(p),
						// p);
					}
					//	getOwnData().discover(p, robot.getBoard().getPanelAt(p));
					if (this.getMergedBoard().getPanelAt(p).hasBarcode()) {
						getOwnData().barcode(
								getMergedBoard().getPanelAt(p).getBarcode(),
								getMergedBoard().getPanelAt(p)
										.getBarcodeOrientation(), p);
					}
				}

	}
	
	private void addToBothBoards(Panel panel, Point point) {
		getMergedBoard().add(panel, getCurrentPoint());
		getOwnBoard().add(panel, getCurrentPoint());
	}
	
	private void addForcedToBothBoards(Panel panel, Point point) {
		getMergedBoard().addForced(panel, getCurrentPoint());
		getOwnBoard().addForced(panel, getCurrentPoint());
	}

	public Orientation getCurrentOrientation()
	{
		return getPathLayer().getOrientationLayer().getOrientation();
	}

	public String getName()
	{
		return name_;
	}

	public Collection<RobotData> getOtherBots()
	{
		return new ArrayList<RobotData>(otherRobots.values());
	}

	public PathLayer getPathLayer()
	{
		return pathLayer;
	}

	public Map<Orientation, Orientation> getRelativeOrientationAfterBarcode(
			RobotDataView firstRobot, RobotDataView secondRobot,
			Point barcodePointFirstRobot, Point barcodePointSecondRobot)
	{
		Panel p1 = firstRobot.getBoard().getPanelAt(barcodePointFirstRobot);
		Panel p2 = secondRobot.getBoard().getPanelAt(barcodePointSecondRobot);
		Orientation orientationBarcodeFirstPanel = p1.getBarcodeOrientation();
		Orientation orientationBarcodeSecondPanel;
		if (p1.getBarcode().getReverse() == p2.getBarcode().getValue()) {
			orientationBarcodeSecondPanel = p2.getBarcodeOrientation();
		} else {
			orientationBarcodeSecondPanel = p2.getBarcodeOrientation()
					.opposite();
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

	public Map<RobotData, Point> getRobotsWithSameBarcode(Barcode barcode)
	{
		if (barcode == null)
			return null;
		Map<RobotData, Point> robots = new HashMap<RobotData, Point>();
		for (RobotData robot : world.get_robots().values()) {
			if (robot.getName().equals(getOwnData().getName()))
				continue;
			for (Barcode code : robot.getBarcodes().keySet()) {
				if (code.equals(barcode))
					robots.put(robot, robot.getBarcodes().get(code));
			}
		}
		return robots;
	}

	public World getWorld()
	{
		return world;
	}

	public void start()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{

				drive();

			}
		}).start();
	}

	private int dist(Point position, Point point)
			throws PathNotPossibleException
	{
		return new DijkstraFinder(getOwnData().getMergedBoard()).shortestPath(position, point)
				.size();
	}

//	private void driveToPacman()
//	{
//		System.out.println("looking for pacman");
//		if (nextToPamam())
//			return;
//		if (getData().getPacmanLastSighted() != null) {
//
//			System.out.println(getData().getPacmanLastSighted());
//			Collection<Point> points = getBoard().getSurrounding(
//					getData().getPacmanLastSighted());
//			points = Filter.filter(points, new Filter<Point>()
//			{
//
//				@Override
//				public boolean accepts(Point arg)
//				{
//
//					return !getBoard().wallBetween(arg,
//							getData().getPacmanLastSighted());
//				}
//			});
//			
//			end: for (Point p : points) {
//
//				try {
//					getPathLayer().go(getCurrentPoint(), p);
//				} catch (IllegalDriveException e) {
//					continue;
//				}
//				break end;
//			
//			}
//
//		}
//		// else{
//		// Point target = null;
//		// for(RobotData data : world.get_robots().values()){
//		// if(data.getPacmanLastSighted()!=null){
//		// //TODO convert target
//		// target = data.getPacmanLastSighted();
//		// }
//		// }
//		// try {
//		// getPathLayer().go(getCurrentPoint(), target);
//		// } catch (IllegalDriveException e) {
//		// e.printStackTrace();
//		// } catch (NullPointerException e){
//		// System.out.println("Geen pacman gevonden!");
//		// }
//		// }
//		else {
//			System.out.println("Geen pacman gevonden!");
//		}
//	}

	public Board getMergedBoard()
	{
		return getOwnData().getMergedBoard();
	}
	
	public Board getOwnBoard() {
		return getOwnData().getBoard();
	}

	public Point getCurrentPoint()
	{
		return getOwnData().getPosition();
	}

	public OwnRobotData getOwnData()
	{
		return (OwnRobotData) world.getRobot(getName());
	}

	private Iterable<Point> filterUnreachable(Point currentPoint,
			RobotData data, Iterable<Point> filledPoints)
	{// TODO:maak floodfil
		ArrayList<Point> rv = new ArrayList<Point>();
		DijkstraFinder d = new DijkstraFinder(getOwnData().getMergedBoard());
		for (Point point : filledPoints) {
			try {
				d.shortestPath(currentPoint, point);
			} catch (PathNotPossibleException e) {
				continue;
			}
			rv.add(point);

		}

		return rv;
	}

	private Point ssClosestPoint()
	{
		Point shortest = null;
		int min = 10000;
		DijkstraFinder f = new DijkstraFinder(getOwnData().getMergedBoard());
		for (Point point : getMergedBoard().getUnfilledPoints()) {
			List<Point> path = null;
			try {
				path = f.shortestPath(getCurrentPoint(), point);
			} catch (PathNotPossibleException e) {
				// Code om deze case op te lossen!
				e.printStackTrace();
			}
			if (min > path.size()) {
				min = path.size();
				shortest = point;
			}
		}
		return shortest;
	}

	private Point ssMostKnown()
	{
		Point shortest = null;
		int max = 0;
		for (Point point : getMergedBoard().getUnfilledPoints()) {
			int c = 1;
			for (Point p : getMergedBoard().getSurrounding(point)) {
				if (getMergedBoard().hasPanelAt(p))
					c++;
			}
			// c = c - dist(getCurrentPoint(), point);
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
		for (Point point : getMergedBoard().getUnfilledPoints()) {
			int c = -1;
			for (Point p : getMergedBoard().getSurrounding(point)) {
				if (!getMergedBoard().hasPanelAt(p))
					c++;
			}
			try {
				c = c - dist(getCurrentPoint(), point);
			} catch (PathNotPossibleException e) {
				// Code om deze case op te lossen.
				e.printStackTrace();
			}
			if (c > max) {
				shortest = point;
				max = c;
			}
		}
		return shortest;
	}

	private void tryAddingOtherRobots()
	{
		synchronized (world.get_robots()) {
			for (RobotData data : world.get_robots().values()) {
				if (!data.getName().equals(this.getName())) {
					if (Transformation.canBeBuild(this.getOwnData(), data)) {
						if (!otherRobots.containsKey(data))
							otherRobots.put(data, new TransformedRobotData(
									new Transformation(this.getOwnData(), data),
									data));
					}
				}
			}
		}
	}

	public boolean hasRobotAt(Point point) {
		for (RobotData data : otherRobots.keySet()) {
			if (data.getPosition().equals(point))
				return true;
		}
		return false;
	}
	
	public boolean pacmanCanMoveToOtherPanel() {
		Point pacmanPosition = getOwnData().getPacmanLastSighted();
		for (Point neighbour : getMergedBoard().getSurrounding(pacmanPosition)) {
			if (!getMergedBoard().wallBetween(pacmanPosition, neighbour) && !hasRobotAt(neighbour))
				return true;
		}
		return false;
	}
}
