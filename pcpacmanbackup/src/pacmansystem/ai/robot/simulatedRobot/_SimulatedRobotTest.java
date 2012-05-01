package pacmansystem.ai.robot.simulatedRobot;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.fysicalRobot.connector.Action;
import pacmansystem.ai.robot.fysicalRobot.connector.Commando;
import pacmansystem.ai.robot.fysicalRobot.connector.PCCommunicator;
import pacmansystem.ai.robot.simulatedRobot.Robot.Robot;
import pacmansystem.ai.robot.simulatedRobot.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.point.Pointfs;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
import data.world.RealWorld;

public class _SimulatedRobotTest
{

	private Ticker ticker;
	private RealWorld rw;
	private Simulation simulation;
	private SimulationConnection conn;
	private float speedmmps=171;
	@Before
	public void simulationTest1()
	{
		ticker = new Ticker();
		rw = RealWorld.getRealWorld(RealWorld.class.getResource(
				"/resources/testworld.txt").getFile());
		conn = new SimulationConnection();
		StandardSimulationBuilder builder = new StandardSimulationBuilder();
		builder.setRealWorld(rw);
		builder.setSimulationConnection(conn);
		builder.setTicker(ticker);
		simulation = builder.build();
		ticker.start();
	}

	@Test
	public void test()
	{
		Robot robot = simulation.getRobot();
		robot.getPilot().setSpeed(360);
		PCCommunicator comm = new PCCommunicator(conn);
		comm.sendCommando(new Commando(Action.FORWARD, 10, "noreason"));
		System.out.println(robot.getLocation());
		sleep(100f/speedmmps+1);
		System.out.println(robot.getLocation());
	}
	private void sleep(float time)
	{
		try {
			Thread.sleep((long)(1000*time));
		} catch (InterruptedException e) {
		}
	}
}
