package pacmansystem.ai.robot.simulatedRobot.point;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.crossing;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.dotProduct;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.in;

import org.junit.Test;

public class PointfsTest
{
	@Test
	public void dotProductTest()
	{
		Pointf one = new Pointf(1, 0);
		Pointf two = new Pointf(0, 1);
		assertTrue(dotProduct(one, two) == -dotProduct(two, one));
	}

	@Test
	public void crossingTest()
	{
		Pointf one = new Pointf(0, 0);
		Pointf two = new Pointf(0, 4);
		Pointf three = new Pointf(-2, 2);
		Pointf four = new Pointf(2, 2);
		assertTrue(crossing(one, two, three, four));
	}

	@Test
	public void crossingTestBorderCase0()
	{
		Pointf one = new Pointf(0, 0);
		Pointf two = new Pointf(0, 2);
		Pointf three = new Pointf(-2, 2);
		Pointf four = new Pointf(2, 2);
		assertTrue(crossing(one, two, three, four));

	}

	@Test
	public void crossingTestBorderCase1()
	{
		Pointf one = new Pointf(0, 0);
		Pointf two = new Pointf(0, 2);
		Pointf three = new Pointf(0, 2);
		Pointf four = new Pointf(2, 2);
		assertTrue(crossing(one, two, three, four));
	}

	@Test
	public void inTest0()
	{
		Pointf one = new Pointf(0, 0);
		Pointf two = new Pointf(-2, 2);
		Pointf three = new Pointf(0, 4);
		Pointf four = new Pointf(2, 2);
		Pointf in = new Pointf(0, 2);
		assertTrue(in(in, one, two, three, four));
	}
	@Test
	public void inTest1()
	{
		Pointf one = new Pointf(0, 0);
		Pointf two = new Pointf(-2, 2);
		Pointf three = new Pointf(0, 4);
		Pointf four = new Pointf(2, 2);
		Pointf in = new Pointf(10, 10);
		assertFalse(in(in, one, two, three, four));
	}
	@Test
	public void inTest2()
	{
		//Bordercase on point
		Pointf one = new Pointf(0, 0);
		Pointf two = new Pointf(-2, 2);
		Pointf three = new Pointf(0, 4);
		Pointf four = new Pointf(2, 2);
		Pointf in = new Pointf(0, 0);
		assertTrue(in(in, one, two, three, four));
	
	}
}
