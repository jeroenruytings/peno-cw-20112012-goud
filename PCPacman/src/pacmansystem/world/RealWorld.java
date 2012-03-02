package pacmansystem.world;

import java.awt.Point;

import pacmansystem.board.Board;

public class RealWorld
{

	private Board globalBoard;
	private Point pacman;
	
	/**
	 * Create a new RealWorld object, given the following parameters.
	 * @param 	pacman
	 * 				The position of the pacman.
	 */
	public RealWorld(Point pacman){
		this.globalBoard = new Board();
		this.pacman = pacman;
	}
	
	public RealWorld(){
		this(null);
	}
	
	/**
	 * @return The global board, with absolute coordinates.
	 */
	public Board getGlobalBoard()
	{
		return globalBoard;
	}

	/**
	 * @return	The location of pacman.
	 */
	public Point getPacmanLocation()
	{
		return pacman;
	}
	
	public void setPacman(Point location){
		this.pacman = location;
	}

}
