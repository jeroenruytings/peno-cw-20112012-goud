package board;

import java.awt.Point;

import org.junit.Test;

public class _ShortestPathFinderTest {
	@Test
	public void twopanelsTest()
	{
		Board one = new Board();
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		one.add(p1, new Point(0,0));
		one.add(p2, new Point(0,1));
		one.add(p2, new Point(0,2));
		ShortestPathFinder finder = new ShortestPathFinder(one);
		Iterable<Point> r =finder.shortestPath(new Point(0,0), new Point(0,2));
		for(Point p:r)
			System.out.println(p);
	}
}
