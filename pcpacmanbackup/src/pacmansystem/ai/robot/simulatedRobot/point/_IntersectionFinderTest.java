package pacmansystem.ai.robot.simulatedRobot.point;

import static org.junit.Assert.*;

import org.junit.Test;

public class _IntersectionFinderTest
{
	@Test
	public void testWithIntersection()
	{
		Pointf P0= new Pointf(1,1);
		Pointf P1 = new Pointf(4,4);
		Pointf p2 = new Pointf(1,8);
		Pointf p3 = new Pointf(2,0);
		InterSectionFinder finder = new InterSectionFinder(P0, P1);
		assertTrue(finder.intersect(p2, p3).X()==finder.intersect(p2,p3).Y());
	}
	@Test
	public void testHorizontalVertical()
	{
		Pointf P0= new Pointf(10,10);
		Pointf P1 = new Pointf(-100,10);
		Pointf p2 = new Pointf(5,100);
		Pointf p3 = new Pointf(5,0);
		InterSectionFinder finder = new InterSectionFinder(P0, P1);
		Pointf inters = finder.intersect(p2, p3);
		assertTrue(inters.equals(new Pointf(5,10)));
	}
	@Test
	public void diagonal()
	{
		Pointf P0= new Pointf(0,0);
		Pointf P1 = new Pointf(10,1);
		Pointf p2 = new Pointf(4,3);
		Pointf p3 = new Pointf(9,0);
		InterSectionFinder finder = new InterSectionFinder(P0, P1);
		Pointf inters = finder.intersect(p2, p3);
		System.out.print(inters);
	}
}
