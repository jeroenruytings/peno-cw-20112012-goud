package pacmansystem.ai.robot.simulatedRobo;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import pacmansystem.ai.robot.simulatedRobo.Robot.CommandoListener;
import pacmansystem.ai.robot.simulatedRobo.Robot.Robot;
import pacmansystem.ai.robot.simulatedRobo.Robot.RobotCommunicator;
import pacmansystem.ai.robot.simulatedRobo.Robot.SensorFacade;
import pacmansystem.ai.robot.simulatedRobo.Robot.SensorListener;
import pacmansystem.ai.robot.simulatedRobo.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobo.Robot.StandardRobotBuilder;
import pacmansystem.ai.robot.simulatedRobo.point.Pointf;
import pacmansystem.ai.robot.simulatedRobo.ticking.Ticker;
import data.world.RealWorld;

public class StandardSimulationBuilder implements SimulationBuilder
{

	private RealWorld realworld;
	private SensorListener sensorlistener;
	private SimulationConnection conn;
	private Ticker ticker;
	private Point point;

	/**
	 * 
	 */
	@Override
	public Simulation build()
	{
		StandardRobotBuilder robotBuilder = new StandardRobotBuilder();
		robotBuilder.setDegrees(0);
		robotBuilder.setOrigin(point);
		robotBuilder.setRealWorld(realworld);
		robotBuilder.setSpeed(5);
		robotBuilder.setTicker(ticker);
		Robot robot = robotBuilder.build();
		SensorFacade s = robot.getSensors();
		RobotCommunicator comm = new RobotCommunicator(new DataInputStream(
				conn.getRobotIN()), new DataOutputStream(conn.getRobotOut()));
		 sensorlistener = new SensorListener(comm,
				s.getUltraSonicSensor(), s.getLightSensor(),
				s.getTouchSensor(), s.getInfraRedSensor(),robot);
		
		CommandoListener l = new CommandoListener(sensorlistener, comm, robot);
		new Thread(l).start();
		ticker.add(sensorlistener);
		Simulation sim = new Simulation(robot, ticker, comm,l);
		return sim;
	}

	public void setTicker(Ticker ticker)
	{
		this.ticker = ticker;
	}

	public void setRealWorld(RealWorld world)
	{
		this.realworld = world;
	}

	public void setSimulationConnection(SimulationConnection conn)
	{
		this.conn = conn;
	}
	public void setStartLocation(Point point)
	{
		this.point=point;
	}
}
