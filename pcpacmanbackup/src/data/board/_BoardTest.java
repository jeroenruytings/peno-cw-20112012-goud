package data.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

import data.board.Panel.WallState;
import data.enums.Orientation;


public class _BoardTest
{

	@Test
	public void OnePanel()
	{
		Board b = new Board(5, 5);
		Panel p = new Panel();
		b.add(p, new Point(0, 0));
		assertTrue(b.hasPanelAt(new Point(0, 0)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void ConflictingPanel()
	{
		Board b = new Board(5, 5);
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		p1.setBorder(Orientation.EAST, WallState.WALL);
		p2.setBorder(Orientation.WEST, WallState.PASSAGE);
		b.add(p1, new Point(0, 0));
		b.add(p2, new Point(1, 0));
	}

	@Test
	public void NonConflictingPanels()
	{
		Board b = new Board(5, 5);
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		p1.setBorder(Orientation.EAST, WallState.WALL);
		p2.setBorder(Orientation.WEST, WallState.WALL);
		b.add(p1, new Point(0, 0));
		b.add(p2, new Point(1, 0));
		assertTrue(b.getPanelAt(new Point(0, 0)).hasBorder(Orientation.EAST) == b
				.getPanelAt(new Point(1, 0)).hasBorder(Orientation.WEST));
	}

	@Test
	public void wallBetweenTest0() throws Exception
	{
		Board b = new Board(5, 5);
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		p1.setBorder(Orientation.EAST, WallState.PASSAGE);
		p2.setBorder(Orientation.WEST, WallState.PASSAGE);
		b.add(p1, new Point(0, 0));
		b.add(p2, new Point(1, 0));
		assertFalse(b.wallBetween(new Point(0, 0), Orientation.EAST));
	}

	@Test
	public void defaultBoardTest()
	{
		//BoardCreator.createWithEdges(10, 10);

		System.out.println("abra");
	}
}
