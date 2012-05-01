package data.world;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JFileChooser;

import communicator.parser.messages.Command;
import communicator.parser.messages.Message;

import data.board.Board;
import data.board.BoardCreator;
import data.lazy.TransformedRobotData;
import data.transformed.Transformation;

public class RealWorld implements Observer
{
	private World managedWorld = new World();

	private Board globalBoard;
	private Point pacman;
	// private Map<String, Point> _startingLocations;
	private List<Point> _startinpos = new ArrayList<Point>();

	private String myName;

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

	public void setMe(String me)
	{
		this.myName = me;
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

	public Point getStartingPoint()
	{
		return _startinpos.get(startingPointIndex++);
	}

	public static RealWorld getRealWorld(String string)
	{
		Scanner scr;
		try {
			scr = new Scanner(new File(string));
		} catch (FileNotFoundException e) {
			throw new Error("loading of file:" + string + " failed");
		}
		ArrayList<String> tmp = new ArrayList<String>();
		while (scr.hasNext()) {
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

	public static RealWorld getRealWorld(File file)
	{
		Scanner scr;
		try {
			scr = new Scanner(file);
		} catch (FileNotFoundException e1) {
			throw new Error("file does not exist");
		}

		ArrayList<String> tmp = new ArrayList<String>();
		while (scr.hasNext()) {
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
	public static RealWorld getRealWorld()
	{
		JFileChooser fileWindow = new JFileChooser();
		fileWindow.showOpenDialog(null);
		Scanner scr = null;
		try {
			scr = new Scanner(fileWindow.getSelectedFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<String> tmp = new ArrayList<String>();
		while (scr.hasNext()) {
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

	@Override
	public void update(Observable o, Object arg)
	{
		new Command((Message) arg).execute(managedWorld);
	}

	/**
	 * Must be called after all panels have been added to the globalboard. in
	 * Boardcreator
	 */
	public void init()
	{
		me = new RobotData(getGlobalBoard());
	}

	private Map<RobotData, RobotDataView> otherBots = new HashMap<RobotData, RobotDataView>();

	/**
	 * Gives the collection of robotDataView objects that represent the other
	 * robots in the simulation.
	 * 
	 * @return
	 */
	public Collection<RobotDataView> getDatas()
	{
		// magic dont even bother
		for (RobotData data : managedWorld.get_robots().values()) {
			if (me(data))
				continue;
			if (otherBots.containsKey(data))
				continue;
			if (!Transformation.canBeBuild(me(), data))
				continue;
			otherBots.put(data, new TransformedRobotData(new Transformation(
					me(), data), data));
		}
		return otherBots.values();
	}

	private RobotData me;

	private RobotData me()
	{
		return me;
	}

	private boolean me(RobotData data)
	{

		return data.getName().equals(myName);
	}

}
