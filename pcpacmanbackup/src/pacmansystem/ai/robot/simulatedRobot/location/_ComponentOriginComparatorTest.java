package pacmansystem.ai.robot.simulatedRobot.location;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pacmansystem.ai.robot.simulatedRobot.location.components.WallComponent;
import pacmansystem.ai.robot.simulatedRobot.point.InterSectionFinder;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

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
		float d = comparator.distance(wall);
		InterSectionFinder finder = new InterSectionFinder(new Pointf(10,10),new Pointf(-100,100));
		System.out.println(finder.intersect(new Pointf(5,100),new Pointf(5,0)));
		System.out.println(d);
	}
}
