package pacmansystem.ai.robot.simulatedRobo.location;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pacmansystem.ai.robot.simulatedRobo.location.components.WallComponent;
import pacmansystem.ai.robot.simulatedRobo.point.Pointf;

public class _ComponentOriginComparatorTest
{
	@Test
	public void test0()
	{
		ComponentOriginComparator comparator = new ComponentOriginComparator(
				new Pointf(10, 10), new Pointf(-100, 10));
		List<Pointf> list = new ArrayList<Pointf>();
		list.add(new Pointf(-5,0));
		list.add(new Pointf(-5,100));
		list.add(new Pointf(5,100));
		list.add(new Pointf(5,0));
		WallComponent wall = new WallComponent(list, 1);
		assertTrue(comparator.distance(wall)==5);
	}
}
