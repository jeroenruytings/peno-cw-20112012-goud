package pacmansystem.ai;

import java.awt.Point;
import java.io.IOException;
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
import util.board.shortestpathfinder.dijkstra.DijkstraFinder;
import util.enums.Orientation;
import util.world.RobotData;
import util.world.World;

import communicator.be.kuleuven.cs.peno.MessageReceiver;
import communicator.be.kuleuven.cs.peno.MessageSender;

public class RobotController
{

	// private int currentX;
	// private int currentY;
	// private Orientation currentOrientation;

	// private void setCurrentOrientation(Orientation currentOrientation) {
	// this.currentOrientation = currentOrientation;
	// }
	private RobotData data = new RobotData();

	public RobotData getData()
	{
		return data;
	}

	private PathLayer pathLayer;
	private World world;

	private Map<RobotData, PointConvertor> convertors = new HashMap<RobotData, PointConvertor>();

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
			Panel p1 = getPathLayer().getOrientationLayer().getPanel(
					getCurrentOrientation()); // getPanel() moet om zich heen
												// kijken
			if (p1.hasBarcode()) {
				sendBarcode(p1.getBarcode());
				Map<RobotData, Point> robotsWithBarcode = getRobotsWithSameBarcode(p1
						.getBarcode());
				if (robotsWithBarcode != null) {
					for (RobotData robot : robotsWithBarcode.keySet()) {
						mergeBoard(robot, robotsWithBarcode.get(robot));
					}
				}
			}
			getBoard().add(p1, getCurrentPoint()); // voegt panel toe aan board
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
				// destination)); //verandert orientatie
			setCurrentPoint(destination); // verandert huidig punt

		}
	}

	private String pointToString(Point p)
	{
		return p.x + "," + p.y;
	}

	private void checkForNewInfo()
	{
		for (RobotData robot : getConvertors().keySet()) {
			for (Point pointFromBoard : robot.getBoard().getPanels().keySet()) {
				Point pointBoard = getConvertors().get(robot).convert(
						pointFromBoard);
				if (getBoard().getPanelAt(pointBoard) != null) {
					try {
						Panel p = robot.getBoard().getPanelAt(pointFromBoard);
						Panel q = new Panel();
						if (p.hasBarcode()) {
							q.setBarcode(p.getBarcode());
						}
						for (Orientation orientation : getConvertors()
								.get(robot).getOrientations().keySet()) {
							q.setBorder(
									orientation,
									p.hasBorder(getConvertors().get(robot)
											.getOrientations().get(orientation)));
						}
						getBoard().add(q, pointBoard);
					} catch (Exception e) {
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
		for (Point pointFromBoard : robot.getBoard().getPanels().keySet()) {
			Point pointBoard = convertor.convert(pointFromBoard);
			if (getBoard().getPanelAt(pointBoard) != null) {
				try {
					Panel p = robot.getBoard().getPanelAt(pointFromBoard);
					Panel q = new Panel();
					if (p.hasBarcode()) {
						q.setBarcode(p.getBarcode());
					}
					for (Orientation orientation : orientations.keySet()) {
						q.setBorder(orientation,
								p.hasBorder(orientations.get(orientation)));
					}
					getBoard().add(q, pointBoard);
				} catch (Exception e) {
					// paneel geeft conflict => niet toevoegen
					e.printStackTrace();
				}
			}
		}

	}

	private Board getBoard()
	{
		return data.getBoard();
	}

	private void sendBarcode(Barcode barcode)
	{
		try {
			MessageSender.getInstance().sendMessage(
					getName() + " BARCODE " + barcode.getValue() + " "
							+ getCurrentOrientation() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Orientation getCurrentOrientation()
	{

		return getPathLayer().getOrientationLayer().getOrientation();
	}

	private Point getCurrentPoint()
	{
		return new Point(getCurrentX(), getCurrentY());
	}

	private void setCurrentPoint(Point p)
	{
		setCurrentX(p.x);
		setCurrentY(p.y);
	}

	public Point lookForDestination()
	{
		return ssMostUnknown();
		// return ssClosestPoint();
		// Orientation orientation = nextMove();
		// if (orientation == null)
		// destination = searchNext();
		// else
		// destination = new Point(getCurrentX() + orientation.getXPlus(),
		// getCurrentY() + orientation.getYPlus());
		// return destination;
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
		Point position = new Point(getCurrentX(), getCurrentY());
		Orientation best = null;
		int nbUnknowns = 0;
		for (Orientation orientation : Orientation.values()) {
			if (getBoard().wallBetween(position, orientation))
				continue;
			Point possibleDest = new Point(getCurrentX()
					+ orientation.getXPlus(), getCurrentY()
					+ orientation.getYPlus());
			int temp = getBoard().nbOfUnknowns(possibleDest);
			if (temp > nbUnknowns) {
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

	// public RobotController(int rows, int columns,OrientationLayer layer)
	// {
	// world = new World();
	// data = new RobotData();
	// currentX = 0;
	// currentY = 0;
	// currentOrientation = Orientation.NORTH;
	//
	// Panel p1 = new Panel();
	// getBoard().add(p1, new Point(0,0));
	// pathLayer = new PathLayer(getBoard(),layer);
	// MessageReceiver rec;
	// try {
	// rec = new MessageReceiver(world);
	// rec.run();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

	public RobotController(OrientationLayer layer)
	{
		initWorld();
		// currentOrientation = Orientation.NORTH;
		pathLayer = new PathLayer(getData(), layer);
		MessageReceiver rec;
		try {
			rec = new MessageReceiver(world);
			Thread t = new Thread(rec);
			t.start();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	public RobotController(Board b, OrientationLayer layer)

	{
		world = new World();
		data = new RobotData(b);
		world.addRobot(data, "Goud");
		// currentOrientation = Orientation.NORTH;
		pathLayer = new PathLayer(getData(), layer);
		MessageReceiver rec;
		try {
			rec = new MessageReceiver(world);
			Thread t = new Thread(rec);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			if (robot.getBarcodes().equals(barcode)) {
				robots.put(robot, robot.getBarcodes().get(barcode));
			}
		}
		return robots;
	}

	public Map<Orientation, Orientation> getRelativeOrientationAfterBarcode(
			RobotData firstRobot, RobotData secondRobot,
			Point barcodePointFirstRobot, Point barcodePointSecondRobot)
	{
		Panel p1 = firstRobot.getBoard().getPanelAt(barcodePointFirstRobot);
		Panel p2 = firstRobot.getBoard().getPanelAt(barcodePointSecondRobot);
		Orientation orientationBarcodeFirstPanel = p1.getBarcodeOrientation();
		Orientation orientationBarcodeSecondPanel = p2.getBarcodeOrientation();
		Map<Orientation, Orientation> orientations = new HashMap<Orientation, Orientation>();
		int ordinalFirst = orientationBarcodeFirstPanel.ordinal();
		int ordinalSecond = orientationBarcodeFirstPanel.ordinal();
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

	
	private static int robotNumber = 0;
	private void initWorld()
	{
		data = new RobotData();
		world = new World();
		data.setName("Goud" + robotNumber++);
		world.addRobot(getData(), getData().getName());
	}

	public Point getLocation()
	{
		return new Point(getCurrentX(), getCurrentY());
	}

}
