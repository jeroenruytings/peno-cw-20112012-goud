package data.world;

import interfaces.pacmancomponents.BoardDisplay;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.board.Board;
import data.enums.Orientation;

import pacmansystem.ai.robot.Barcode;

public class RobotData implements RobotDataView
{

	private Board board;
	// Pacman's location.
	private Point pacman = null;
	// All the locations pacman was sighted by this robot.
	private List<Point> pacmanHistory;
	// The amount of previous data to be hold.
	private int pacmanhistorylength=100;
	private Orientation orientation = Orientation.NORTH;
	
	private String name;

	private Point position = new Point(0, 0);

	private ArrayList<Point> plan;
	
	private Map<Barcode,Point> barcodes = new HashMap<Barcode,Point>();

//TODO: is dit nodig ?
	public Map<Barcode, Point> getBarcodes() {
		return barcodes;
	}
	public void setBoard(Board board)
	{
		this.board= board;
	}
	public void setName(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see util.world.RobotDataView#getName()
	 */
	@Override
	public String getName(){
		return name;
	}
	
	private boolean capturedPacman;

	/* (non-Javadoc)
	 * @see util.world.RobotDataView#isCapturedPacman()
	 */
	@Override
	public boolean isCapturedPacman()
	{
		return capturedPacman;
	}

	public void setCapturedPacman(boolean capturedPacman)
	{
		this.capturedPacman = capturedPacman;
	}
	public RobotData(String name)
	{
		board = new Board();
		plan = new ArrayList<Point>();
		this.name=name;
		pacmanHistory = new ArrayList<Point>();
	}
	public RobotData(Board board)
	{
		this.board = board;
		plan = new ArrayList<Point>();
		pacmanHistory = new ArrayList<Point>();
	}
	
	public RobotData()
	{
		board = new Board();
		plan = new ArrayList<Point>();
		pacmanHistory = new ArrayList<Point>();
	}

	/**
	 * This method is used if a new location of the pacman is established by the
	 * robot.
	 * 
	 * @param location
	 *            The new location.
	 */
	public void setPacman(Point location)
	{
		if (pacman != null) {
			if (pacmanHistory.size() >= pacmanhistorylength) {
				pacmanHistory.remove(0);
			}
			pacmanHistory.add((Point) pacman.clone());
		}
		pacman = location;
	}

	/* (non-Javadoc)
	 * @see util.world.RobotDataView#getBoard()
	 */
	@Override
	public Board getBoard()
	{//TODO: wille we references meegeven?
		return this.board;
	}

	/* (non-Javadoc)
	 * @see util.world.RobotDataView#getPacmanLastSighted()
	 */
	@Override
	public Point getPacmanLastSighted()
	{
		return pacman;
	}

	/* (non-Javadoc)
	 * @see util.world.RobotDataView#getPosition()
	 */
	@Override
	public Point getPosition()
	{
		return position;
	}
	
	/* (non-Javadoc)
	 * @see util.world.RobotDataView#getOrientation()
	 */
	@Override
	public Orientation getOrientation(){
		return orientation;
	}
	
	/**
	 * @param 	orientation
	 * 				New orientation of the robot.
	 */
	public void setOrientation(Orientation orientation){
		this.orientation = orientation;
	}

	/**
	 * Set a new position for the robot.
	 * 
	 * @param newPosition
	 *            The new position of the robot.
	 */
	public void setPosition(Point newPosition)
	{
		this.position = newPosition;
	}

	/* (non-Javadoc)
	 * @see util.world.RobotDataView#clearPlan()
	 */
	@Override
	public void clearPlan()
	{
		Iterator<Point> it = plan.iterator();
		while (it.hasNext()) {
			it.remove();
			it.next();
		}
	}

	/* (non-Javadoc)
	 * @see util.world.RobotDataView#addPlan(java.util.ArrayList)
	 */
	@Override
	public void addPlan(ArrayList<Point> newPlan)
	{
		clearPlan();
		plan = newPlan;
	}
	
	/* (non-Javadoc)
	 * @see util.world.RobotDataView#toString()
	 */
	@Override
	public String toString(){
		return getName();
	}

	private Color robotColor = BoardDisplay.getRandomColor();
	/* (non-Javadoc)
	 * @see util.world.RobotDataView#getRobotColor()
	 */
	@Override
	public Color getRobotColor() {
		return robotColor;
	}

	public void setRobotColor(Color robotColor) {
		this.robotColor = robotColor;
		
	}
	@Override
	public void pong(String name, String message) {
				
	}
	  
}