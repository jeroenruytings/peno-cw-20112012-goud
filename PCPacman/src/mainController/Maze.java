package mainController;

import mainController.MainController.Orientation;

public class Maze {

	Square[][] square;
	
	public Maze(int rows, int columns){
		square = new Square[rows][columns];
	}
	
	/**
	 * 
	 * @param one
	 * @param two
	 * @pre de twee squares liggen naast elkaar.
	 * @return	twee als er geen muur is
	 * @return	één als er WEL een muur is
	 * @return	nul als het onbekend is
	 */
	public static int wallBetween(Square one, Square two){
		
		if(one.equals(two))
			throw new IllegalArgumentException();
		
		if(one.getXCoordinate() == two.getXCoordinate()){
			if(one.getYCoordinate() > two.getYCoordinate()){
				return one.getWallSouth();
			}
			return one.getWallNorth();
		}
		else{
			if(one.getXCoordinate() > two.getXCoordinate()){
				return one.getWallWest();
			}
			return one.getWallEast();
		}
	}
	
	public Square nextSquare(Square one, Orientation orientation){		
		return square[one.getYCoordinate() + orientation.getXPlus()][ orientation.getYPlus() + one.getYCoordinate()];
	}
	
}
