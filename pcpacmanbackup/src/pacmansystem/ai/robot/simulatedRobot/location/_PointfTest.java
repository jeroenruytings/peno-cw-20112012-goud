package pacmansystem.ai.robot.simulatedRobot.location;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

public class _PointfTest
{
	@Test
	public void testCreation1()
	{
		Pointf point = new Pointf(10, 12);
		assertTrue(point.X() == 10);
		assertTrue(point.Y() == 12);
	}

	@Test
	public void testCreation2()
	{
		Pointf point = new Pointf(10, 12);
		assertTrue(point.xy()[0] == 10);
		assertTrue(point.xy()[1] == 12);
	}

	@Test
	public void copyTest()
	{
		Pointf point = new Pointf(10, 12);
		point.xy()[0] = 5;
		assertTrue(point.xy()[0] == 10);
	}

}
