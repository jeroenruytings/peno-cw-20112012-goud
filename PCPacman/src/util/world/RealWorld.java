package util.world;

import java.awt.Point;
import java.util.Map;
import java.util.Stack;

import util.board.Board;

public class RealWorld
{

	private Board globalBoard;
	private Point pacman;
	private Map<String, Point> _startingLocations;
	private Stack<Point> _startinpos = new Stack<Point>();

	/**
	 * Create a new RealWorld object, given the following parameters.
	 * 
	 * @param pacman
	 *            The position of the pacman.
	 */
	public RealWorld(Point pacman)
	{
		this.globalBoard = new Board();
		this.pacman = pacman;
	}

	public RealWorld()
	{
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
	 * @return The location of pacman.
	 */
	public Point getPacmanLocation()
	{
		return pacman;
	}

	public void setPacman(Point location)
	{
		this.pacman = location;
	}

	public void addStartingPoint(Point position)
	{
		_startinpos.add(position);
	}

	public Point getStartingPoint()
	{

		return _startinpos.pop();
	}

}
