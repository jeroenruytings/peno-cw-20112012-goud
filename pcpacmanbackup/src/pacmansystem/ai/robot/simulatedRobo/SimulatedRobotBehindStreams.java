package pacmansystem.ai.robot.simulatedRobo;

import java.awt.Point;

import pacmansystem.ai.robot.simulatedRobo.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobo.location.RealWorldViewFromRealWorldObject;
import pacmansystem.ai.robot.simulatedRobo.location.RealWorldViewNoMoving;
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
		
		new Thread(sim).start();
	}
	public SimulationConnection getConnection(){
		return connection_;
	}
}
