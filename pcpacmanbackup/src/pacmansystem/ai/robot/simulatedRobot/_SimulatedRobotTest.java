package pacmansystem.ai.robot.simulatedRobot;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.fysicalRobot.PanelLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.CrashedException;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.simulatedRobot.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
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
//		panellayer.go(Direction.UP);
//		layer.turnHead(0);
		//synced :D
	System.out.println(simulation.getRobot().getSensors().getLightSensor().getLightValue());
	System.out.println(panellayer.getBarcode());
	System.out.println("done");
	}

	private void sleep(float time)
	{
		try {
			Thread.sleep((long) (1000 * time));
		} catch (InterruptedException e) {
		}
	}
}
