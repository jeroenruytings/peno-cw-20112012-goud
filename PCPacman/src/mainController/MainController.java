package mainController;

import java.awt.Point;

import board.Board;
import direction.Direction;

public class MainController {
	
	private int currentX;
	private int currentY;
	private Orientation currentOrientation;
	private Board board;
	private IndexPQ pq;
	private int rows;
	private int columns;
	
	public static void main(String[] args) {
		int rows = Integer.parseInt(args[0]);
		int columns = Integer.parseInt(args[1]);
		MainController main = new MainController(rows, columns);
		main.nextMove();
	}
	
	private Orientation nextMove() {
		Point currentPoint = new Point(getCurrentX(),getCurrentY());
		Orientation best = null;
		int nbUnknowns=-1;
		for(Orientation orientation: Orientation.values()){
			if (board.wallBetween(currentPoint, orientation))
				continue;
			Point point = new Point(getCurrentX()+orientation.getXPlus(),getCurrentY()+orientation.getYPlus());
			int temp = board.nbOfUnknowns(point);
			if(temp > nbUnknowns){
				best = orientation;
				nbUnknowns = temp;
			}
		}
		return best;
	}
	
	

	public MainController(int rows, int columns){
		currentX = 0;
		currentY = 0;
		currentOrientation = Orientation.NORTH;
		board = new Board();
		this.rows = rows;
		this.columns = columns;
		pq = new IndexPQ(rows*columns);
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
