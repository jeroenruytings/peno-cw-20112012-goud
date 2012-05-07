package pacmansystem.ai.robot.simulatedRobo.point;

import org.junit.Test;

public class SegmentIteratorTest
{
	@Test
	public void test0()
	{
		Pointf p0 = new Pointf(0,1);
		Pointf p1 = new Pointf(1,1);
		Pointf p2 = new Pointf(1,0);
		for(Segment s : new SegmentIterator(p0,p1,p2).getIterable())
			System.out.println(s.one+":"+s.two);
	}
}
