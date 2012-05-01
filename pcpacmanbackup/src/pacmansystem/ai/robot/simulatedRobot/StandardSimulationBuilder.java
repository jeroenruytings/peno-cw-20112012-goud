package pacmansystem.ai.robot.simulatedRobot;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import pacmansystem.ai.robot.simulatedRobot.Robot.CommandoListener;
import pacmansystem.ai.robot.simulatedRobot.Robot.Robot;
import pacmansystem.ai.robot.simulatedRobot.Robot.RobotCommunicator;
import pacmansystem.ai.robot.simulatedRobot.Robot.SensorFacade;
import pacmansystem.ai.robot.simulatedRobot.Robot.SensorListener;
import pacmansystem.ai.robot.simulatedRobot.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobot.Robot.StandardRobotBuilder;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
import data.world.RealWorld;

public class StandardSimulationBuilder implements SimulationBuilder
{

	private RealWorld realworld;
	private SensorListener sensorlistener;
	private SimulationConnection conn;
	private Ticker ticker;

	/**
	 * 
	 */
	@Override
	public Simulation build()
	{
		StandardRobotBuilder robotBuilder = new StandardRobotBuilder();
		robotBuilder.setDegrees(0);
		robotBuilder.setOrigin(new Point(0, 0));
		robotBuilder.setRealWorld(realworld);
		robotBuilder.setSpeed(5);
		robotBuilder.setTicker(ticker);
		Robot robot = robotBuilder.build();
		RobotCommunicator comm = new RobotCommunicator(new DataInputStream(
				conn.getRobotIN()), new DataOutputStream(conn.getRobotOut()));
		CommandoListener l = new CommandoListener(sensorlistener, comm,
				robot);
		new Thread(l).start();
		SensorFacade s = robot.getSensors();
		SensorListener listener = new SensorListener(comm, s.getUltraSonicSensor(),s.getLightSensor(),s.getTouchSensor(), s.getInfraRedSensor());
		ticker.add(listener);
		Simulation sim = new Simulation(robot, ticker, comm);
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

}
