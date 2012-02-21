package MainController;

public class MainController {
	
	private int currentX;
	private int currentY;
	private Orientation currentOrientation;
	private Maze maze;
	
	public MainController(int rows, int columns){
		currentX = 0;
		currentY = 0;
		currentOrientation = Orientation.NORTH;
		maze = new Maze(rows, columns);
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
	
	public enum Orientation{
		NORTH,
		SOUTH,
		WEST,
		EAST;
	}
}
