package pacmansystem.ai.robot.simulatedRobot;

import java.awt.Point;

import pacmansystem.ai.robot.simulatedRobot.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobot.location.LocationManager;
import data.enums.Orientation;
import data.world.RealWorld;

public class SimulatedRobotBehindStreams
{
	private LocationManager locationManager_;
	private SimulationConnection connection_;
	private Simulation sim;
	public SimulatedRobotBehindStreams(RealWorld world,Point startposition,Orientation startOrientation)
	{
		locationManager_=new LocationManager(world,startposition,startOrientation);
		connection_ = new SimulationConnection();
		new Thread(new Simulation(connection_.getRobotIN(), connection_.getRobotOut())).start();
	}
	public SimulationConnection getConnection(){
		return connection_;
	}
}
