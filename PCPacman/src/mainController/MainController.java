package mainController;

import board.Board;
import direction.Direction;

public class MainController {
	
	private int currentX;
	private int currentY;
	private Orientation currentOrientation;
	private Board board;
	
	public MainController(int rows, int columns){
		currentX = 0;
		currentY = 0;
		currentOrientation = Orientation.NORTH;
		board = new Board();
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
