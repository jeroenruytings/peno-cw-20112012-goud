package pacmansystem.ai;

import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import communicator.be.kuleuven.cs.peno.MessageReceiver;
import communicator.be.kuleuven.cs.peno.MessageSender;


import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PathLayer;
import util.board.Board;
import util.board.Panel;
import util.board.PointConvertor;
import util.enums.Orientation;
import util.world.RobotData;
import util.world.World;


public class RobotController
{

	//private int currentX;
	//private int currentY;
	private Orientation currentOrientation;
	
	public void setCurrentOrientation(Orientation currentOrientation) {
		this.currentOrientation = currentOrientation;
	}

	private RobotData data = new RobotData();
	
	public RobotData getData() {
		return data;
	}

	private PathLayer pathLayer;
	private World world;
	
	private Map<RobotData,PointConvertor> convertors;

	public Map<RobotData, PointConvertor> getConvertors() {
		return convertors;
	}


	public PathLayer getPathLayer() {
		return pathLayer;
	}

	private void join() {
		try {
			MessageSender.getInstance().sendMessage("JOIN\n");
			MessageSender.getInstance().sendMessage("goud NAME\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void explore(){
		Point destination = null;
		boolean finished = false;
		while (!finished) {
			checkForNewInfo();
			Panel p1 = getPathLayer().getDirectionLayer().getPanel(getCurrentOrientation()); //getPanel() moet om zich heen kijken
			if(p1.hasBarcode()){
				sendBarcode(p1.getBarcode());
				HashMap<RobotData,Point> robotsWithBarcode = (HashMap<RobotData, Point>) getRobotsWithSameBarcode(p1.getBarcode());
				if(robotsWithBarcode != null){
					for(RobotData robot : robotsWithBarcode.keySet()){
						mergeBoard(robot,robotsWithBarcode.get(robot));
					}
				}
			}			
			getBoard().add(p1, getCurrentPoint()); //voegt panel toe aan board
			for (Orientation orientation : Orientation.values()) { //voegt omliggende punten toe indien ze geen tussenmuur hebben
				if(! p1.hasBorder(orientation)){
					Panel p2 = new Panel();
					p2.setBorder(orientation.opposite(), true);
					getBoard().add(p2, new Point(getCurrentX()+orientation.getXPlus(),getCurrentY()+orientation.getYPlus()));
				}
			}
			try {
				destination = lookForDestination(); //zoekt volgend punt om naartoe te gaan
			} catch (NullPointerException e) {
				finished = true;
			}
			getPathLayer().go(getCurrentPoint(), destination); //gaat naar volgend punt
			setCurrentOrientation(Board.getOrientationBetween(getCurrentPoint(), destination)); //verandert orientatie
			setCurrentPoint(destination); //verandert huidig punt
			try {
				MessageSender.getInstance().sendMessage("goud DISCOVER "+ getCurrentPoint()+ " " +
						getBoard().getPanelAt(getCurrentPoint()).bordersToString() +"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	
	
	private void checkForNewInfo() {
		for(RobotData robot : getConvertors().keySet()){
			for(Point pointFromBoard : robot.getBoard().getPanels().keySet()){
				Point pointBoard = getConvertors().get(robot).convert(pointFromBoard);
				if(getBoard().getPanelAt(pointBoard) != null){
					try {
						Panel p = robot.getBoard().getPanelAt(pointFromBoard);
						Panel q = new Panel();
						if(p.hasBarcode()){
							q.setBarcode(p.getBarcode());
						}
						for(Orientation orientation : getConvertors().get(robot).getOrientations().keySet()){
							q.setBorder(orientation, p.hasBorder(getConvertors().get(robot).getOrientations().get(orientation)));
						}
						getBoard().add(q, pointBoard);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	private void mergeBoard(RobotData robot, Point point) {
		HashMap<Orientation,Orientation> orientations = (HashMap<Orientation, Orientation>) getRelativeOrientationAfterBarcode(getData(), robot, getCurrentPoint(), point);
		PointConvertor convertor = new PointConvertor(getCurrentPoint(), point, orientations);
		getConvertors().put(robot, convertor);
		for(Point pointFromBoard : robot.getBoard().getPanels().keySet()){
			Point pointBoard = convertor.convert(pointFromBoard);
			if(getBoard().getPanelAt(pointBoard) != null){
				try {
					Panel p = robot.getBoard().getPanelAt(pointFromBoard);
					Panel q = new Panel();
					if(p.hasBarcode()){
						q.setBarcode(p.getBarcode());
					}
					for(Orientation orientation : orientations.keySet()){
						q.setBorder(orientation, p.hasBorder(orientations.get(orientation)));
					}
					getBoard().add(q, pointBoard);
				} catch (Exception e) {
					//paneel geeft conflict => niet toevoegen
				}
			}
		}
		
	}

	private Board getBoard()
	{	return data.getBoard();
	}

	private void sendBarcode(Barcode barcode) {
		try {
			MessageSender.getInstance().sendMessage("goud BARCODE "+barcode.getValue()+" "+getCurrentOrientation()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Orientation getCurrentOrientation() {
		return currentOrientation;
	}

	private Point getCurrentPoint() {
		return new Point(getCurrentX(),getCurrentY());
	}
	
	private void setCurrentPoint(Point p){
		setCurrentX((int) p.getX());
		setCurrentY((int) p.getY());
	}

	public Point lookForDestination()
	{
		Point destination;
		Orientation orientation = nextMove();
		if (orientation == null)
			destination = searchNext();
		else
			destination = new Point(getCurrentX() + orientation.getXPlus(),
					getCurrentY() + orientation.getYPlus());
		return destination;
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
			if(point.equals(getCurrentPoint()))
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

//	public RobotController(int rows, int columns,OrientationLayer layer)
//	{
//		world = new World();
//		data = new RobotData();
//		currentX = 0;
//		currentY = 0;
//		currentOrientation = Orientation.NORTH;
//		
//		Panel p1 = new Panel();
//		getBoard().add(p1, new Point(0,0));
//		pathLayer = new PathLayer(getBoard(),layer);
//		MessageReceiver rec;
//		try {
//			rec = new MessageReceiver(world);
//			rec.run();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}

	public RobotController(OrientationLayer layer)
	{
		initWorld();
		currentOrientation = Orientation.NORTH;
		pathLayer = new PathLayer(getBoard(), layer);
		MessageReceiver rec;
		try {
			rec = new MessageReceiver(world);
			rec.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public RobotController(Board b,OrientationLayer layer)

	{
		world = new World();
		data = new RobotData(b);
		world.addRobot(data, "Goud");
		currentOrientation = Orientation.NORTH;
		pathLayer = new PathLayer(getBoard(), layer);
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
	
	public Map<RobotData,Point> getRobotsWithSameBarcode(Barcode barcode){
		
		if(barcode == null)
			return null;
		Map<RobotData,Point> robots = new HashMap<RobotData, Point>();
		for(RobotData robot : world.get_robots().values()){
			if(robot.getBarcodes().equals(barcode)){
				robots.put(robot, robot.getBarcodes().get(barcode));
			}
		}
		return robots;
	}
	
	public Map<Orientation,Orientation> getRelativeOrientationAfterBarcode(RobotData firstRobot, RobotData secondRobot,
			Point barcodePointFirstRobot, Point barcodePointSecondRobot){
		Panel p1 = firstRobot.getBoard().getPanelAt(barcodePointFirstRobot);
		Panel p2 = firstRobot.getBoard().getPanelAt(barcodePointSecondRobot);
		Orientation orientationBarcodeFirstPanel = p1.getBarcodeOrientation();
		Orientation orientationBarcodeSecondPanel = p2.getBarcodeOrientation();
		Map<Orientation, Orientation> orientations = new HashMap<Orientation, Orientation>();
		int ordinalFirst = orientationBarcodeFirstPanel.ordinal();
		int ordinalSecond = orientationBarcodeFirstPanel.ordinal();
		orientations.put(orientationBarcodeFirstPanel, orientationBarcodeSecondPanel);
		orientations.put(orientationBarcodeFirstPanel.opposite(),orientationBarcodeSecondPanel.opposite());
		ordinalFirst = (ordinalFirst+1) % 4;
		ordinalSecond = (ordinalSecond+1) % 4;
		orientationBarcodeFirstPanel = Orientation.fromOrdinal(ordinalFirst);
		orientationBarcodeSecondPanel = Orientation.fromOrdinal(ordinalSecond);
		orientations.put(orientationBarcodeFirstPanel, orientationBarcodeSecondPanel);
		orientations.put(orientationBarcodeFirstPanel.opposite(),orientationBarcodeSecondPanel.opposite());
		return orientations;
	}

	
	private void initWorld(){
		data = new RobotData();
		world = new World();
		world.addRobot(getData(), "Goud" + Math.random());
	}
	
	
}
