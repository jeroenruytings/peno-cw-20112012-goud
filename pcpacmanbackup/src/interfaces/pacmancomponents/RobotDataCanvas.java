package interfaces.pacmancomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import util.board.Board;
import util.world.RobotData;

public class RobotDataCanvas extends BoardDisplay
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RobotData data;
	
	
	public RobotDataCanvas(RobotData robot) {
		super();
		addRobotData(robot);
		data = robot;
	}

	
	
	protected void drawPacman(Graphics g, Point position, Color color){
		super.drawPacman(g, position, Color.YELLOW);
	}

	@Override
	public Board getBoard() {
		return data.getBoard();
	}


	public void setRobotColor(Color robotColor) {
		data.setRobotColor(robotColor);
	}
	
	
	
	
//	public void addRobotData(RobotData newData){
//		super.addRobotData(newData);
//		setBoard(newData.getBoard());
//		getRobots().remove(data);
//		data = newData;
//		
//	}

	
}
