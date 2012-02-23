package mainController;

import direction.Direction;
import mainController.MainController.Orientation;

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
		NORTH(-1,0),
		SOUTH(1,0),
		WEST(0,-1),
		EAST(0,1);
		
		private int xPlus;
		private int yPlus;
		
		private Orientation(int x, int y){
			xPlus = x;
			yPlus = y;
		}
		
		public int getXPlus () {
			return xPlus;
		}
		
		public int getYPlus () {
			return yPlus;
		}

		public static Orientation fromOrdinal(int i) {
			
			for(Orientation o:Orientation.values())
				if(o.ordinal()==i)
					return o;
			return null;
		}
		public Orientation addTo(Direction dir)
		{
			switch (this) {
			
			case NORTH:
				switch(dir)
				{
				case LEFT:
					return WEST;
				case UP:
				return this;
				
				case RIGHT:
					return EAST;
				case DOWN:
					return SOUTH;
				
				
				}
				break;
			case EAST:
				switch(dir)
				{
				case LEFT:
					return NORTH;
				case UP:
					return this;
				case RIGHT:
					return SOUTH;
				case DOWN:
					return WEST;
				
				
				}
				break;

			case WEST:
				switch(dir)
				{
				case LEFT:
				return SOUTH;
				case UP:
					return this;
				case RIGHT:
					return NORTH;
				case DOWN:
					return EAST;
				
				
				}
				break;

			case SOUTH:
				switch(dir)
				{
				case LEFT:
					return EAST;
				case UP:
					return this;
				case RIGHT:
					return WEST;
				case DOWN:
					return NORTH;
				
				
				}
				break;

				
			default:
				break;
			}
		
			return null;
		}
	}
	
	
}
