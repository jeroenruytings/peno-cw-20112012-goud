package pacmansystem.ai.robot.simulatedRobot.location;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class _RealWorldViewBuilderTest
{
	private RealWorldViewFromRealWorldObject builder;

	@Before
	public void init()
	{
		builder = new RealWorldViewFromRealWorldObject(null);
	}

	@Test
	public void testNorth()
	{

		Collection<Pointf> points = builder.northPointsWall(new Point(0, 0));
		assertTrue(points.contains(new Pointf(0, SIMINFO.PANELHEIGHT
				- SIMINFO.WALLHEIGHT / 2)));
		assertTrue(points.contains(new Pointf(0, SIMINFO.PANELHEIGHT
				+ SIMINFO.WALLHEIGHT / 2)));
		assertTrue(points.contains(new Pointf(SIMINFO.PANELWIDTH,
				SIMINFO.PANELHEIGHT + SIMINFO.WALLHEIGHT / 2)));
		assertTrue(points.contains(new Pointf(SIMINFO.PANELWIDTH,
				SIMINFO.PANELHEIGHT - SIMINFO.WALLHEIGHT / 2)));
	}

	@Test
	public void testEast()
	{
		Collection<Pointf> points = builder.eastPointsWall(new Point(0, 0));
		assertTrue(points.contains(new Pointf(SIMINFO.PANELWIDTH
				- SIMINFO.WALLHEIGHT / 2, SIMINFO.PANELHEIGHT)));
		assertTrue(points.contains(new Pointf(SIMINFO.PANELWIDTH
				- SIMINFO.WALLHEIGHT / 2, 0)));
		assertTrue(points.contains(new Pointf(SIMINFO.PANELWIDTH
				+ SIMINFO.WALLHEIGHT / 2, 0)));
		assertTrue(points.contains(new Pointf(SIMINFO.PANELWIDTH
				+ SIMINFO.WALLHEIGHT / 2, SIMINFO.PANELHEIGHT)));
	}

	@Test
	public void testSouth()
	{
		Collection<Pointf> points = builder.southPointsWall(new Point(0, 0));
		assertTrue(points.contains(new Pointf(0, -SIMINFO.WALLHEIGHT / 2)));
		assertTrue(points.contains(new Pointf(0, SIMINFO.WALLHEIGHT / 2)));
		assertTrue(points.contains(new Pointf(SIMINFO.PANELWIDTH,
				SIMINFO.WALLHEIGHT / 2)));
		assertTrue(points.contains(new Pointf(SIMINFO.PANELWIDTH,
				SIMINFO.WALLHEIGHT / 2)));
	}

	@Test
	public void testWest()
	{
		Collection<Pointf> points = builder.westPointsWall(new Point(0, 0));
		assertTrue(points.contains(new Pointf(-SIMINFO.WALLHEIGHT / 2, 0)));
		assertTrue(points.contains(new Pointf(SIMINFO.WALLHEIGHT / 2, 0)));
		assertTrue(points.contains(new Pointf(-SIMINFO.WALLHEIGHT / 2,
				SIMINFO.PANELHEIGHT)));
		assertTrue(points.contains(new Pointf(+SIMINFO.WALLHEIGHT / 2,
				SIMINFO.PANELHEIGHT)));
	}

	@Test
	public void testNorth2()
	{

		for (int x = 0; x < 100; x++)
			for (int y = 0; y < 100; y++) {
				Collection<Pointf> points = builder.northPointsWall(new Point(
						x, y));
				assertTrue(points.contains(new Pointf(x * SIMINFO.PANELWIDTH, y
						* SIMINFO.PANELHEIGHT + SIMINFO.PANELHEIGHT
						- SIMINFO.WALLHEIGHT / 2)));
				assertTrue(points.contains(new Pointf(x * SIMINFO.PANELWIDTH, y
						* SIMINFO.PANELHEIGHT + SIMINFO.PANELHEIGHT
						+ SIMINFO.WALLHEIGHT / 2)));
				assertTrue(points.contains(new Pointf(x * SIMINFO.PANELWIDTH
						+ SIMINFO.PANELWIDTH, y * SIMINFO.PANELHEIGHT
						+ SIMINFO.PANELHEIGHT + SIMINFO.WALLHEIGHT / 2)));
				assertTrue(points.contains(new Pointf(x * SIMINFO.PANELWIDTH
						+ SIMINFO.PANELWIDTH, y * SIMINFO.PANELHEIGHT
						+ SIMINFO.PANELHEIGHT - SIMINFO.WALLHEIGHT / 2)));
			}
	}
}
