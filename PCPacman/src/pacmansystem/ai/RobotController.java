package pacmansystem.ai;

import java.awt.Point;
import java.io.IOException;

import be.kuleuven.cs.peno.MessageSender;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.DirectionLayer;
import pacmansystem.ai.robot.PathLayer;
import pacmansystem.board.Board;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Orientation;
import pacmansystem.world.RobotData;

import pacmansystem.world.RealWorld;


public class RobotController extends RobotData
{

	private int currentX;
	private int currentY;
	private Orientation currentOrientation;
	private Board board;

	private PathLayer pathLayer;
	private MessageSender sender;

	public MessageSender getSender() {
		return sender;
	}

	public PathLayer getPathLayer() {
		return pathLayer;
	}

	private void join() {
		try {
			getSender().sendMessage("JOIN\n");
			getSender().sendMessage("goud NAME\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void explore(){
		Point destination = null;
		boolean finished = false;
		while (!finished) {
			Panel p1 = getPathLayer().getDirectionLayer().getPanel(getCurrentOrientation()); //getPanel() moet om zich heen kijken
			if(p1.hasBarcode()){
				sendBarcode(p1.getBarcode());
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
			setCurrentPoint(destination); //verandert huidig punt
			try {
				getSender().sendMessage("goud DISCOVER "+ getCurrentX() + "," + getCurrentY()+ " " +
						getBoard().getPanelAt(getCurrentPoint()).bordersToString() +"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	
	
	private void sendBarcode(Barcode barcode) {
		try {
			getSender().sendMessage("\n"); //TODO
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
			if (board.wallBetween(position, orientation))
				continue;
			Point possibleDest = new Point(getCurrentX()
					+ orientation.getXPlus(), getCurrentY()
					+ orientation.getYPlus());
			int temp = board.nbOfUnknowns(possibleDest);
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
		for (Point point : board.getPanels().keySet()) {
			int nbKnown = 4 - board.nbOfUnknowns(point);
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
		return (int) (Math.abs(destination.getX() - currentX) + Math
				.abs(destination.getY() - currentY));
	}

	public RobotController(int rows, int columns,PathLayer layer)
	{
		sender = null;
		try {
			sender = new MessageSender();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentX = 0;
		currentY = 0;
		currentOrientation = Orientation.NORTH;
		board = new Board(rows, columns);
		Panel p1 = new Panel();
		board.add(p1, new Point(0,0));
		pathLayer = layer;
	}

	public RobotController(DirectionLayer layer)

	{
		super();
		sender = null;
		try {
			sender = new MessageSender();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentX = 0;
		currentY = 0;
		currentOrientation = Orientation.NORTH;
		pathLayer = new PathLayer(getBoard(), layer);
	}
	
	public RobotController(Board b,DirectionLayer layer)

	{
		super(b);
		sender = null;
		try {
			sender = new MessageSender();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.board = getBoard();
		currentX = 0;
		currentY = 0;
		currentOrientation = Orientation.NORTH;
		pathLayer = new PathLayer(getBoard(), layer);
	}
	
//	public RobotController(RealWorld realworld, int rows, int columns) 
//	{
//		sender = null;
//		try {
//			sender = new MessageSender();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		currentX = 0;
//		currentY = 0;
//		currentOrientation = Orientation.NORTH;
//		board = new Board(rows, columns);
//		Panel p1 = new Panel();
//		board.add(p1, new Point(0,0));
//		pathLayer = new PathLayer(board,realworld);
//	}
//	
//	public RobotController(RealWorld realworld, Board board)
//	{
//		sender = null;
//		try {
//			sender = new MessageSender();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.board = board;
//		currentX = 0;
//		currentY = 0;
//		currentOrientation = Orientation.NORTH;
//		pathLayer = new PathLayer(board, realworld);
//	}

	public int getCurrentX()
	{
		return currentX;
	}

	public void setCurrentX(int currentX)
	{
		this.currentX = currentX;
	}

	public int getCurrentY()
	{
		return currentY;
	}

	public void setCurrentY(int currentY)
	{
		this.currentY = currentY;
	}

}
