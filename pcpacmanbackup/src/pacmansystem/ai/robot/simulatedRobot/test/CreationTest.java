package pacmansystem.ai.robot.simulatedRobot.test;

import java.awt.Point;

import interfaces.mainscreen.Mainscreen;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.PCCommunicator;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobotBehindStreams;

import data.enums.Orientation;
import data.world.RealWorld;

public class CreationTest
{
	private RealWorld realWorld;
	private MoverLayer moverlayer;
	@Before
	public void before()
	{
		realWorld = RealWorld.getRealWorld(CreationTest.class.getResource(
				"\resources\testworld.txt").getFile());
		SimulatedRobotBehindStreams sim = new SimulatedRobotBehindStreams(
				realWorld, new Point(0, 0), Orientation.NORTH);
	//	moverlayer = new MoverLayer(new PCCommunicator(sim.getConnection()));

	}

	@Test
	public void test0()
	{
		//moverlayer.drive(3);
	}
}
