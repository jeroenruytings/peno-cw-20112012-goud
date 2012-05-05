package pacmansystem.ai.robot.simulatedRobot;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.simulatedRobot.Robot.Robot;
import pacmansystem.ai.robot.simulatedRobot.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
import data.world.RealWorld;

public class _SimulatedRobotTest
{

	private Ticker ticker;
	private RealWorld rw;
	private Simulation simulation;
	private SimulationConnection conn;
	private float speedmmps = 171;
	private MoverLayer layer;

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
		builder.setStartLocation(new Point(0,0));
		simulation = builder.build();
		ticker.start();
		layer = new MoverLayer(conn);
	}

	@Test
	public void test()
	{
		layer.turnHead(90);
		System.out.println("Abra.");
	}

	private void sleep(float time)
	{
		try {
			Thread.sleep((long) (1000 * time));
		} catch (InterruptedException e) {
		}
	}
}
