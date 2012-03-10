package interfaces.pacmancomponents;

import util.world.RobotData;

public class RobotDataCanvas extends BoardDisplay
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public RobotDataCanvas(RobotData robot) {
		super(robot.getBoard());
		addRobotData(robot);
	}
	
//	public void addRobotData(RobotData newData){
//		super.addRobotData(newData);
//		setBoard(newData.getBoard());
//		getRobots().remove(data);
//		data = newData;
//		
//	}

	
}
