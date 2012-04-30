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
import static org.junit.Assert.*;
import static pacmansystem.ai.robot.simulatedRobot.SIMINFO.*;
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
		// LINKER ONDERHOEK
		assertTrue(comps.get(0).getConvexPoints().contains(new Pointf(0,(PANELHEIGHT/2) - (BARCODEHEIGHT * 4))));
		assertEquals(((OpenComponent) comps.get(0)).getColor(), BLACK);
		// RECHTER ONDERHOEK
		assertTrue(comps.get(2).getConvexPoints().contains(new Pointf(PANELWIDTH,(PANELHEIGHT/2) - (BARCODEHEIGHT * 1))));
		assertEquals(((OpenComponent) comps.get(2)).getColor(), WHITE);
		// RECHTER BOVENHOEK
		assertTrue(comps.get(4).getConvexPoints().contains(new Pointf(PANELWIDTH,(PANELHEIGHT/2) - (BARCODEHEIGHT * 0))));
		assertEquals(((OpenComponent) comps.get(4)).getColor(), BLACK);
		// LINKER BOVENHOEK
		assertTrue(comps.get(6).getConvexPoints().contains(new Pointf(0,(PANELHEIGHT/2) + (BARCODEHEIGHT * 3))));
		assertEquals(((OpenComponent) comps.get(6)).getColor(), WHITE);
	}
	@Test
	public void testEast()
	{
		List<LocationComponent> comps = builder.genEastBarcodes(new Point(0,0), new Barcode(1,1,1,0,1,1));
		// LINKER ONDERHOEK
		assertTrue(comps.get(0).getConvexPoints().contains(new Pointf((PANELWIDTH/2) - (BARCODEHEIGHT * 4),0)));
		assertEquals(((OpenComponent) comps.get(0)).getColor(), BLACK);
		// RECHTER ONDERHOEK
		assertTrue(comps.get(2).getConvexPoints().contains(new Pointf((PANELWIDTH/2) - (BARCODEHEIGHT * 1),0)));
		assertEquals(((OpenComponent) comps.get(2)).getColor(), WHITE);
		// RECHTER BOVENHOEK
		assertTrue(comps.get(4).getConvexPoints().contains(new Pointf((PANELWIDTH/2) + (BARCODEHEIGHT * 1),PANELHEIGHT)));
		assertEquals(((OpenComponent) comps.get(4)).getColor(), BLACK);
		// LINKER BOVENHOEK
		assertTrue(comps.get(6).getConvexPoints().contains(new Pointf((PANELWIDTH/2) + (BARCODEHEIGHT * 2),PANELHEIGHT)));
		assertEquals(((OpenComponent) comps.get(6)).getColor(), WHITE);
	}
	@Test
	public void testWest()
	{
		List<LocationComponent> comps = builder.genWestBarcode(new Point(0,0), new Barcode(1,1,1,0,1,1));
		// LINKER ONDERHOEK
		assertTrue(comps.get(0).getConvexPoints().contains(new Pointf((PANELWIDTH/2) + (BARCODEHEIGHT * 4),0)));
		assertEquals(((OpenComponent) comps.get(0)).getColor(), BLACK);
		// RECHTER ONDERHOEK
		assertTrue(comps.get(2).getConvexPoints().contains(new Pointf((PANELWIDTH/2) + (BARCODEHEIGHT * 1),0)));
		assertEquals(((OpenComponent) comps.get(2)).getColor(), WHITE);
		// RECHTER BOVENHOEK
		assertTrue(comps.get(4).getConvexPoints().contains(new Pointf((PANELWIDTH/2) - (BARCODEHEIGHT * 1),PANELHEIGHT)));
		assertEquals(((OpenComponent) comps.get(4)).getColor(), BLACK);
		// LINKER BOVENHOEK
		assertTrue(comps.get(6).getConvexPoints().contains(new Pointf((PANELWIDTH/2) - (BARCODEHEIGHT * 2),PANELHEIGHT)));
		assertEquals(((OpenComponent) comps.get(6)).getColor(), WHITE);
	}
	@Test
	public void testSouth()
	{
		List<LocationComponent> comps = builder.genSouthBarcode(new Point(0,0), new Barcode(1,1,1,0,1,1));
		// LINKER ONDERHOEK
		assertTrue(comps.get(0).getConvexPoints().contains(new Pointf(0,(PANELHEIGHT/2) + (BARCODEHEIGHT * 4))));
		assertEquals(((OpenComponent) comps.get(0)).getColor(), BLACK);
		// RECHTER ONDERHOEK
		assertTrue(comps.get(2).getConvexPoints().contains(new Pointf(PANELWIDTH,(PANELHEIGHT/2) + (BARCODEHEIGHT * 1))));
		assertEquals(((OpenComponent) comps.get(2)).getColor(), WHITE);
		// RECHTER BOVENHOEK
		assertTrue(comps.get(4).getConvexPoints().contains(new Pointf(PANELWIDTH,(PANELHEIGHT/2) + (BARCODEHEIGHT * 0))));
		assertEquals(((OpenComponent) comps.get(4)).getColor(), BLACK);
		// LINKER BOVENHOEK
		assertTrue(comps.get(6).getConvexPoints().contains(new Pointf(0,(PANELHEIGHT/2) - (BARCODEHEIGHT * 3))));
		assertEquals(((OpenComponent) comps.get(6)).getColor(), WHITE);
	}
}
