package pacmansystem.ai.robot.simulatedRobo.Robot.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.world.RealWorld;

import pacmansystem.ai.robot.simulatedRobo.Robot.Robot;
import pacmansystem.ai.robot.simulatedRobo.Robot.SensorHolder;
import pacmansystem.ai.robot.simulatedRobo.Robot.StandardRobotBuilder;
import pacmansystem.ai.robot.simulatedRobo.point.Pointf;
import pacmansystem.ai.robot.simulatedRobo.ticking.Ticker;

public class _StandardRobotTest
{
	Robot robot;

	@Before
	public void init()
	{
		RealWorld rw = RealWorld.getRealWorld(RealWorld.class.getResource(
				"/resources/testworld.txt").getFile());
		StandardRobotBuilder builder = new StandardRobotBuilder();
		builder.setDegrees(0);
		builder.setOrigin(new Point(0, 0));
		builder.setSpeed(5);
		builder.setRealWorld(rw);
		builder.setTicker(new Ticker());
		robot = builder.build();
	}

	@Test
	public void testConvex()
	{
		List<Pointf> convexHull = Robot.convexAround(robot.getLocation(),
				robot.getDirection());
	}

	@Test
	public void testSenshorHolder()
	{
		Ticker t = new Ticker();
		final Object lockl = new String();
		final SensorHolder holder = robot.getHead();
		double direction = robot.getHead().getUltraSonicSensor().getDirection();
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				robot.getHead().rotate(50);
				robot.getPilot().rotate(10);
				System.out.println("done");
				synchronized (lockl) {
					lockl.notify();

				}
			}
		}).start();
		t.add(holder);
		t.add(robot);
		t.add(robot.getPilot());
		t.start();
		synchronized (lockl) {
			try {
				lockl.wait();
			} catch (InterruptedException e) {
			}
		}
		
	}
}
