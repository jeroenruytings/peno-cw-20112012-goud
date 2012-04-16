package data.world;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import data.board.Board;
import data.board.BoardCreator;


public class RealWorld
{

	private Board globalBoard;
	private Point pacman;
	//private Map<String, Point> _startingLocations;
	private List<Point> _startinpos = new ArrayList<Point>();

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

	private static int startingPointIndex = 0;
	public Point getStartingPoint(int index)
	{

		return _startinpos.get(index);
	}
	
	public Point getStartingPoint(){
		return _startinpos.get(startingPointIndex++);
	}

	public static RealWorld getRealWorld(String string)
	{
		Scanner scr;
		try {
			scr = new Scanner(new File(string));
		} catch (FileNotFoundException e) {
			throw new Error("loading of file:"+ string+" failed");
		}
		ArrayList<String> tmp = new ArrayList<String>();
		while(scr.hasNext()){
			String command = scr.nextLine();
			tmp.add(command);
		}
		RealWorld result = null;
		String[] commands = new String[10];
		try {
			result = BoardCreator.createBoard(tmp.toArray(commands));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;	
	}

	/**
	 * Ask a RealWorld from the user.
	 */
	public static RealWorld getRealWorld(){
		JFileChooser fileWindow = new JFileChooser();
		fileWindow.showOpenDialog(null);
		Scanner scr = null;
		try {
			scr = new Scanner(fileWindow.getSelectedFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<String> tmp = new ArrayList<String>();
		while(scr.hasNext()){
			String command = scr.nextLine();
			tmp.add(command);
		}
		RealWorld result = null;
		String[] commands = new String[10];
		try {
			result = BoardCreator.createBoard(tmp.toArray(commands));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
