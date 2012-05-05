package pacmansystem.ai.robot.simulatedRobot;

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
		MoverLayer layer = new MoverLayer(conn);
//		while (true)
//			System.out.println(layer.getLightSensor());
	}

	private void sleep(float time)
	{
		try {
			Thread.sleep((long) (1000 * time));
		} catch (InterruptedException e) {
		}
	}
}
