package pacmansystem.ai.robot;

import org.junit.Test;

import data.enums.Direction;
import data.enums.Orientation;


public class _OrientationLayerTest
{
	@Test
	public void test()
	{
		Orientation start = Orientation.WEST;
		Orientation current = Orientation.SOUTH;
		Direction d=Direction.DOWN;
		for(Direction dir : Direction.values())
			if(start == current.addTo(dir))
				d=dir;
		System.out.println(d);
	}
}
