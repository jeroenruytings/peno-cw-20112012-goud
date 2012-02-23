package board;

import java.awt.Point;

import org.junit.Test;

import board.Panel.Orientation;

public class _ShortestPathFinderTest {
	@Test
	public void twopanelsTest()
	{
		Board one = new Board();
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		p1.setBorder(Orientation.EAST, true);
		p2.setBorder(Orientation.WEST, true);
		one.add(p1, new Point(0,0));
		one.add(p2, new Point(1,0));
		//one.add(p3, new Point(1,1));
		one.add(p3, new Point(0,1));
		ShortestPathFinder finder = new ShortestPathFinder(one);
		Iterable<Point> r =finder.shortestPath(new Point(0,0), new Point(1,0));
		for(Point p:r)
			System.out.println(p);
	}
}
