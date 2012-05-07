package pacmansystem.ai.robot.simulatedRobo;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.fysicalRobot.PanelLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.CrashedException;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.simulatedRobo.Robot.Robot;
import pacmansystem.ai.robot.simulatedRobo.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobo.ticking.Ticker;
import data.enums.Direction;
import data.world.RealWorld;

public class _SimulatedRobotTest
{

	private Ticker ticker;
	private RealWorld rw;
	private Simulation simulation;
	private SimulationConnection conn;
	private float speedmmps = 171;
	private MoverLayer layer;
	private PanelLayer panellayer;

	@Before
	public void simulationTest1()
	{
		ticker = new Ticker();
		rw = RealWorld.getRealWorld(RealWorld.class.getResource(
				"/resources/OnePanelTestMaze.txt").getFile());
		conn = new SimulationConnection();
		StandardSimulationBuilder builder = new StandardSimulationBuilder();
		builder.setRealWorld(rw);
		builder.setSimulationConnection(conn);
		builder.setTicker(ticker);
		builder.setStartLocation(new Point(0, 1));
		simulation = builder.build();
		simulation.start();
		layer = new MoverLayer(conn);
		panellayer = new PanelLayer(layer);
	}

	@Test
	public void test() throws CrashedException
	{
		Robot r = simulation.getRobot();
		r.getPilot().rotate(180);
		r.getPilot().travel(10);
		r.getPilot().rotate(180);
		r.getPilot().travel(10);
		r.getPilot().rotate(180);
		r.getPilot().travel(10);
		r.getPilot().rotate(180);
		r.getPilot().travel(10);
		r.getPilot().rotate(90);
		
	}

	private void sleep(float time)
	{
		try {
			Thread.sleep((long) (1000 * time));
		} catch (InterruptedException e) {
		}
	}
}
