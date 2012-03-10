package interfaces.pacmancomponents;

import util.world.RobotData;

public class SimRobotDataDisplay extends BoardDisplay
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RobotData data;
	
	
	public SimRobotDataDisplay() {
		super(null);
	}
	
	public void addRobotData(RobotData newData){
		super.addRobotData(newData);
		setBoard(newData.getBoard());
		getRobots().remove(data);
		data = newData;
		
	}

	
}
