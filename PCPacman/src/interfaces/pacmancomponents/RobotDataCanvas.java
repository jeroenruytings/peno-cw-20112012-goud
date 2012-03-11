package interfaces.pacmancomponents;

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


	@Override
	public Board getBoard() {
		return data.getBoard();
	}
	
	
	
	
//	public void addRobotData(RobotData newData){
//		super.addRobotData(newData);
//		setBoard(newData.getBoard());
//		getRobots().remove(data);
//		data = newData;
//		
//	}

	
}
