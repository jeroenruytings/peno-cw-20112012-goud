package pacmansystem.ai.robot.simulatedRobot;

import java.awt.Point;

import pacmansystem.ai.robot.simulatedRobot.location.LocationManager;
import data.enums.Orientation;
import data.world.RealWorld;

public class SimulatedRobotBehindStreams
{
	private LocationManager locationManager_;
	private SimulationConnection connection_;
	private OwnSimulatedConnection myconnection;
	
	public SimulatedRobotBehindStreams(RealWorld world,Point startposition,Orientation startOrientation)
	{
		locationManager_=new LocationManager(world,startposition,startOrientation);
		connection_ = new SimulationConnection();
		myconnection = connection_.getOwn();
	}
	public SimulationConnection getConnection(){
		return connection_;
	}
}
