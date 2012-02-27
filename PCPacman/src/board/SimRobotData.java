package board;


import java.awt.Point;
import java.util.List;


public class SimRobotData {

	
	private RobotBoard board;
	// Pacman's location.
	private Point pacman;
	// All the locations pacman was sighted by this robot.
	private List<Point> pacmanHistory;
	// The amount of previous data to be hold.
	private int pacmanhistorylength;
	
	private Simulator overview;
	
	public SimRobotData(Simulator sim,RobotBoard board){
		this.board = board;
		this.overview = sim;
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
	public RobotBoard getBoard(){
		return this.board;
	}
	
	/**
	 * @return	The location pacman was last seen by this robot.
	 */
	public Point getPacmanLastSighted(){
		return pacman;
	}
	
	
}
