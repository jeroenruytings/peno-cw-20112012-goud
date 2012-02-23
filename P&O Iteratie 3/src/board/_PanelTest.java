package board;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import board.Panel.Direction;

public class _PanelTest {

	@Test
	public void NoBorders() {
		Panel a = new Panel();
		for(Direction d: Direction.values())
			assertFalse(a.getBorder(d));
	}
	@Test
	public void OneBorder()
	{
		Panel a = new Panel();
		a.setBorder(Direction.UP, true);
		assertTrue(a.getBorder(Direction.UP));
	}	
	@Test
	public void TwoBorders()
	{
		Panel a = new Panel();
		a.setBorder(Direction.UP, true);
		assertTrue(a.getBorder(Direction.UP));
		a.setBorder(Direction.DOWN, true);
		assertTrue(a.getBorder(Direction.DOWN));
	}
	@Test
	public void DirectionTestUP()
	{
		Point a 		= 	new Point(0,0);
		Point result	=	Direction.UP.addTo(a);
		assertTrue(result.equals(new Point(0,-1)));
	}
	@Test
	public void DirectionTestLeft()
	{
		Point a 		= 	new Point(0,0);
		Point result	=	Direction.LEFT.addTo(a);
		assertTrue(result.equals(new Point(-1,0)));
	}
	@Test
	public void DirectionTestDOWN()
	{
		Point a 		= 	new Point(0,0);
		Point result	=	Direction.DOWN.addTo(a);
		assertTrue(result.equals(new Point(0,1)));
	}
	@Test
	public void DirectionTestRIGHT()
	{
		Point a 		= 	new Point(0,0);
		Point result	=	Direction.RIGHT.addTo(a);
		assertTrue(result.equals(new Point(1,0)));
	}
	

}
