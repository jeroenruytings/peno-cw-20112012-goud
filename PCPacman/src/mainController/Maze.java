package mainController;

import java.util.Random;

import mainController.MainController.Orientation;

public class Maze {

	Square[][] square;
	
	public Square[][] getSquare() {
		return square;
	}



	public static void main(String[] args) {
		Maze maze = new Maze(10,10);
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				Square square = new Square(i,j);
				square.setWallEast(2);
				square.setWallNorth(2);
				square.setWallSouth(2);
				square.setWallWest(2);
				maze.getSquare()[i][j] = square;
			}
		}
		EuclideanGraph graph = new EuclideanGraph(maze.getSquare());    // <========= hier oneindige lus!
		Dijkstra dijk = new Dijkstra(graph);
		for(int i = 0; i<100; i++){
			for(int j = 0; j<100; j++){
				dijk.distance(i, j);
				dijk.showPath(i, j);
			}
		}
	}
	
	
	
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
		
		if(one.equals(null) || two.equals(null))
			throw new NullPointerException();
		
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
