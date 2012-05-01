package pacmansystem.ai.robot.simulatedRobot.Robot.test;

import java.awt.Point;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.world.RealWorld;

import pacmansystem.ai.robot.simulatedRobot.Robot.Robot;
import pacmansystem.ai.robot.simulatedRobot.Robot.StandardRobotBuilder;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

public class _StandardRobotTest
{
	Robot robot;
	@Before
	public void init()
	{
		RealWorld rw = RealWorld.getRealWorld(RealWorld.class.getResource("/resources/testworld.txt").getFile());
		StandardRobotBuilder builder = new StandardRobotBuilder();
		builder.setDegrees(0);
		builder.setOrigin(new Point(0,0));
		builder.setSpeed(5);
		builder.setRealWorld(rw);
		robot = builder.build();
	}
	@Test
	public void testConvex()
	{
		List<Pointf> convexHull = Robot.convexAround(robot.getLocation(), robot.getDirection());
	}
}
