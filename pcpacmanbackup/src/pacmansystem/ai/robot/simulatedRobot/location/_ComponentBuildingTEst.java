package pacmansystem.ai.robot.simulatedRobot.location;

import java.awt.Point;
import java.util.Collection;
import java.util.List;

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
		List<LocationComponent> comps = builder.genNorthBarcodes(new Point(0,0), new Barcode(1,1,1,0,1,1));
		
	
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
