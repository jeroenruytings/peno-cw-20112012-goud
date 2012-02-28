package mainController;

import java.awt.Point;
import java.io.ObjectInputStream.GetField;

import board.Board;
import direction.Direction;

public class RobotController {
	
	private int currentX;
	private int currentY;
	private Orientation currentOrientation;
	private Board board;
	
	public static void main(String[] args) {
		int rows = Integer.parseInt(args[0]);
		int columns = Integer.parseInt(args[1]);
		RobotController main = new RobotController(rows, columns);
		Point destination = null;
		while (true){
			//kijk om u heen
			destination = main.lookForDestination();
			//ga naar destination en pas currentX en Y aan
		}
	}

	public Point lookForDestination() {
		Point destination;
		Orientation orientation = nextMove();
		if (orientation == null)
			destination = searchNext();
		else
			destination = new Point (getCurrentX() + orientation.getXPlus(), getCurrentY() + orientation.getYPlus());
		return destination;
	}
	
	
	/**
	 * 
	 * @return	de ori�ntatie waar je naartoe moet.
	 * @return 	null als alle omliggende vakjes gekend zijn.
	 */
	private Orientation nextMove() {
		Point position = new Point(getCurrentX(),getCurrentY());
		Orientation best = null;
		int nbUnknowns=0;
		for(Orientation orientation: Orientation.values()){
			if (board.wallBetween(position, orientation))
				continue;
			Point possibleDest = new Point(getCurrentX()+orientation.getXPlus(),getCurrentY()+orientation.getYPlus());
			int temp = board.nbOfUnknowns(possibleDest);
			if(temp > nbUnknowns){			
				best = orientation;
				nbUnknowns = temp;
			}
		}
		return best;
	}
	
	/**
	 * 
	 * @return	het beste punt om naar toe te rijden
	 * @return 	null als alle punten gekend zijn.
	 */
	private Point searchNext() {
		Point best = null;
		int waarde = 1000;
		for (Point point: board.getPanels().keySet()){
			int nbKnown = 4 - board.nbOfUnknowns(point);
			if (nbKnown == 4)
				continue;
			int temp = nbKnown + heuristiek(point);
			if (temp < waarde){
				best = point;
				waarde = temp;
			}
		}
		return best;
	}
	
	private int heuristiek (Point destination){
		return (int) (Math.abs(destination.getX() - currentX) + Math.abs(destination.getY() - currentY));
	}
	
	public RobotController(int rows, int columns){
		currentX = 0;
		currentY = 0;
		currentOrientation = Orientation.NORTH;
		board = new Board(rows, columns);
	}
	
	public RobotController(Board board) {
		this.board = board;
		currentX = 0;
		currentY = 0;
		currentOrientation = Orientation.NORTH;
	}

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
	

	
}
