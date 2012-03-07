package util.board;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import util.enums.Orientation;

public class _PanelTest
{

	@Test
	public void NoBorders()
	{
		Panel a = new Panel();
		for (Orientation d : Orientation.values())
			assertFalse(a.hasBorder(d));
	}

	@Test
	public void OneBorder()
	{
		Panel a = new Panel();
		a.setBorder(Orientation.NORTH, true);
		assertTrue(a.hasBorder(Orientation.NORTH));
	}

	@Test
	public void TwoBorders()
	{
		Panel a = new Panel();
		a.setBorder(Orientation.NORTH, true);
		assertTrue(a.hasBorder(Orientation.NORTH));
		a.setBorder(Orientation.SOUTH, true);
		assertTrue(a.hasBorder(Orientation.SOUTH));
	}

	@Test
	public void DirectionTestUP()
	{
		Point a = new Point(0, 0);
		Point result = Orientation.NORTH.addTo(a);
		assertTrue(result.equals(new Point(0, -1)));
	}

	@Test
	public void DirectionTestLeft()
	{
		Point a = new Point(0, 0);
		Point result = Orientation.WEST.addTo(a);
		assertTrue(result.equals(new Point(-1, 0)));
	}

	@Test
	public void DirectionTestDOWN()
	{
		Point a = new Point(0, 0);
		Point result = Orientation.SOUTH.addTo(a);
		assertTrue(result.equals(new Point(0, 1)));
	}

	@Test
	public void DirectionTestRIGHT()
	{
		Point a = new Point(0, 0);
		Point result = Orientation.EAST.addTo(a);
		assertTrue(result.equals(new Point(1, 0)));
	}

}
