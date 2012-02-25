package board;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import board.Panel.Orientation;

public class _BoardTest {
	
	@Test
	public void OnePanel()
	{
		Board b = new Board();
		Panel p = new Panel();
		b.add(p, new Point(0,0));
		assertTrue(b.hasPanelAt(new Point(0,0)));
	}
	@Test(expected = IllegalArgumentException.class)
	public void ConflictingPanel()
	{
	Board b = new Board();
	Panel p1 = new Panel();
	Panel p2 = new Panel();
	p1.setBorder(Orientation.EAST,true);
	p2.setBorder(Orientation.WEST, false);
	b.add(p1, new Point(0,0));
	b.add(p2, new Point(1,0));	
	}
	@Test
	public void NonConflictingPanels()
	{
	Board b = new Board();
	Panel p1 = new Panel();
	Panel p2 = new Panel();
	p1.setBorder(Orientation.EAST,true);
	p2.setBorder(Orientation.WEST, true);
	b.add(p1, new Point(0,0));
	b.add(p2, new Point(1,0));
	assertTrue(b.getPanelAt(new Point(0,0)).getBorder(Orientation.EAST)==b.getPanelAt(new Point(1,0)).getBorder(Orientation.WEST));
	}
	@Test
	public void wallBetweenTest0() throws Exception
	{
		Board b = new Board();
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		p1.setBorder(Orientation.EAST,false);
		p2.setBorder(Orientation.WEST, false);
		b.add(p1, new Point(0,0));
		b.add(p2, new Point(1,0));
		assertFalse(b.wallBetween(new Point(0,0), Orientation.EAST));
	}
}