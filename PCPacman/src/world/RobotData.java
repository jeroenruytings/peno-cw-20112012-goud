package world;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import board.Board;


public class RobotData {

	
	private Board board;
	// Pacman's location.
	private Point pacman;
	// All the locations pacman was sighted by this robot.
	private List<Point> pacmanHistory;
	// The amount of previous data to be hold.
	private int pacmanhistorylength;
	
	private Point position = new Point(0, 0);
	
	private ArrayList<Point> plan;
	
	private boolean capturedPacman;
	
	public boolean isCapturedPacman() {
		return capturedPacman;
	}

	public void setCapturedPacman(boolean capturedPacman) {
		this.capturedPacman = capturedPacman;
	}

	public RobotData(Board board){
		this.board = board;
		plan = new ArrayList<Point>();
	}
	
	public RobotData(){
		board = new Board();
		plan = new ArrayList<Point>();
	}
	
	/**
	 * This method is used if a new location of the pacman is established by the robot.
	 * @param 	location
	 * 				The new location.
	 */
	public void setPacman(Point location){
		if (pacman != null){
			if (pacmanHistory.size() >= pacmanhistorylength){
				pacmanHistory.remove(0);
			}
			pacmanHistory.add((Point)pacman.clone());
		}
		pacman = location;
	}
	
	/**
	 * @return The board this robot has created.
	 */
	public Board getBoard(){
		return this.board;
	}
	
	/**
	 * @return	The location pacman was last seen by this robot.
	 */
	public Point getPacmanLastSighted(){
		return pacman;
	}
	
	/**
	 * @return	The position of the robot.
	 */
	public Point getPosition(){
		return position;
	}
	
	/**
	 * Set a new position for the robot.
	 * @param 	newPosition
	 * 				The new position of the robot.
	 */
	public void setPosition(Point newPosition){
		this.position = newPosition;
	}

	public void clearPlan() {
		Iterator<Point> it = plan.iterator();
		while(it.hasNext()){
			it.remove();
			it.next();
		}
	}
	
	public void addPlan(ArrayList<Point> newPlan){
		clearPlan();
		plan = newPlan;
	}
}
