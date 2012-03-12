package util.world;

import interfaces.pacmancomponents.BoardDisplay;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pacmansystem.ai.robot.Barcode;
import util.board.Board;
import util.enums.Orientation;

public class RobotData
{

	private Board board;
	// Pacman's location.
	private Point pacman;
	// All the locations pacman was sighted by this robot.
	private List<Point> pacmanHistory;
	// The amount of previous data to be hold.
	private int pacmanhistorylength;
	private Orientation orientation = Orientation.NORTH;
	
	private String name;

	private Point position = new Point(0, 0);

	private ArrayList<Point> plan;
	
	private Map<Barcode,Point> barcodes;

	public Map<Barcode, Point> getBarcodes() {
		return barcodes;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	private boolean capturedPacman;

	public boolean isCapturedPacman()
	{
		return capturedPacman;
	}

	public void setCapturedPacman(boolean capturedPacman)
	{
		this.capturedPacman = capturedPacman;
	}

	public RobotData(Board board)
	{
		this.board = board;
		plan = new ArrayList<Point>();
	}

	public RobotData()
	{
		board = new Board();
		plan = new ArrayList<Point>();
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

	/**
	 * @return The board this robot has created.
	 */
	public Board getBoard()
	{
		return this.board;
	}

	/**
	 * @return The location pacman was last seen by this robot.
	 */
	public Point getPacmanLastSighted()
	{
		return pacman;
	}

	/**
	 * @return The position of the robot.
	 */
	public Point getPosition()
	{
		return position;
	}
	
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

	public void clearPlan()
	{
		Iterator<Point> it = plan.iterator();
		while (it.hasNext()) {
			it.remove();
			it.next();
		}
	}

	public void addPlan(ArrayList<Point> newPlan)
	{
		clearPlan();
		plan = newPlan;
	}
	
	public String toString(){
		return getName();
	}

	private Color robotColor = BoardDisplay.getRandomColor();
	public Color getRobotColor() {
		return robotColor;
	}

	public void setRobotColor(Color robotColor) {
		this.robotColor = robotColor;
		
	}
}
