package pacmansystem.ai.robot.simulatedRobot;

import java.awt.Point;

import pacmansystem.ai.robot.simulatedRobot.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobot.location.RealWorldViewNoMoving;
import pacmansystem.ai.robot.simulatedRobot.location.RealWorldViewFromRealWorldObject;
import data.enums.Orientation;
import data.world.RealWorld;

public class SimulatedRobotBehindStreams
{
	private RealWorldViewNoMoving locationManager_;
	private SimulationConnection connection_;
	private Simulation sim;
	public SimulatedRobotBehindStreams(RealWorld world,Point startposition,Orientation startOrientation)
	{
		locationManager_=new RealWorldViewFromRealWorldObject(world).build();
		connection_ = new SimulationConnection();
		sim = new Simulation(connection_.getRobotIN(), connection_.getRobotOut());
		new Thread(sim).start();
	}
	public SimulationConnection getConnection(){
		return connection_;
	}
}
