package pacmansystem.ai.robot.simulatedRobot.location;

import java.awt.Point;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.OpenComponent;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

public class _ComponentBuildingTEst
{
	RealWorldViewFromRealWorldObject builder;
	@Before
	public void init()
	{
		builder = new RealWorldViewFromRealWorldObject(null);
	}
	@Test
	public void testNorth()
	{
		Collection<LocationComponent> comps = builder.genNorthBarcodes(new Point(0,0), new Barcode(1,1,1,0,1,1));
		for(LocationComponent component : comps)
		{
			System.out.println("Color:"+((OpenComponent)component).getColor());
			for(Pointf point:component.getConvexPoints())
				System.out.println(point);
		}
	}
	@Test
	public void testEast()
	{
		
	}
	@Test
	public void testWest()
	{
		
	}
	@Test
	public void testSouth()
	{
		
	}
}
