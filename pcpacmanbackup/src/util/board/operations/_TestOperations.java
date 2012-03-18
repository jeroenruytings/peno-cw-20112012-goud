package util.board.operations;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

import util.board.Board;
import util.board.Panel;
import util.board.operations.Operations.Turn;

public class _TestOperations
{
	@Test
	public void testTurnEnumLeft()
	{
		
		assertTrue(Turn.LEFT.exec(new Point(1,0)).equals(new Point(0,1)));
	}
	@Test
	public void testTurnEnumLeft2()
	{
		
		assertTrue(Turn.LEFT.exec(new Point(0,1)).equals(new Point(-1,0)));
	}
	@Test
	public void testTurnEnumRight()
	{
		
		assertTrue(Turn.RIGHT.exec(new Point(0,1)).equals(new Point(1,0)));
	}
	
	@Test
	public void testTurnEnumRigh1t()
	{
		
		assertTrue(Turn.RIGHT.exec(new Point(-1,0)).equals(new Point(0,1)));
	}
	@Test
	public void negateTest()
	{
		assertTrue(new Point(1,1).equals(Operations.negate(new Point(-1,-1))));
	}
	@Test
	public void translatePointTest()
	{
		assertTrue(new Point(1,1).equals(Operations.translate(new Point(2,2),Operations.negate(new Point(1,1)))));
	}
	@Test
	public void turnPointAroundTest()
	{
		Point p1 = new Point(1,1);
		Point around = new Point(1,0);
		Point result = new Point(0,0);
		assertTrue(Operations.turn(p1, around, Turn.LEFT).equals(result));
		
	}
	
	@Test
	public void testBoardTurn0()
	{
		Board board = new Board();
		Panel[] panels=panels(20);
		for (int i = 0; i < panels.length; i++) {
			board.add(panels[i], new Point(i,3));
		}
		Board turned =Operations.turn(board, new Point(1,3), Turn.LEFT);
		for(Point p:turned.getFilledPoints())
			assertTrue(p.x==1);
		
	}
	private Panel[] panels(int i)
	{
		Panel[]  rv = new Panel[i];
		for (int j = 0; j < rv.length; j++) {
			rv[j]=new Panel();
		}
		return rv;
	}
}
