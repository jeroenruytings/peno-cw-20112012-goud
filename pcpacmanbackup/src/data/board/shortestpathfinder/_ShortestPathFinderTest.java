package data.board.shortestpathfinder;

import java.awt.Point;

import org.junit.Test;

import data.board.Board;
import data.board.Panel;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;


public class _ShortestPathFinderTest
{
	// @Test
	// public void twopanelsTest()
	// {
	// Board one = new Board();
	// Panel p1 = new Panel();
	// Panel p2 = new Panel();
	// Panel p3 = new Panel();
	// p1.setBorder(Orientation.EAST, true);
	// p2.setBorder(Orientation.WEST, true);
	// one.add(p1, new Point(0,0));
	// one.add(p2, new Point(1,0));
	// //one.add(p3, new Point(1,1));
	// one.add(p3, new Point(0,1));
	// ShortestPathFinder finder = new ShortestPathFinder(one);
	// Iterable<Point> r =finder.shortestPath(new Point(0,0), new Point(1,0));
	// for(Point p:r)
	// System.out.println(p);
	// }

	@Test
	public void panelsTest()
	{
//		Board one = new Board(10, 10);
//		Panel p1 = new Panel();
//		Panel p2 = new Panel();
//		Panel p3 = new Panel();
//		Panel p4 = new Panel();
//		Panel p5 = new Panel();
//		Panel p6 = new Panel();
//		Panel p7 = new Panel();
//		Panel p8 = new Panel();
//		Panel p9 = new Panel();
//		p1.setBorder(Orientation.SOUTH, true);
//		p2.setBorder(Orientation.SOUTH, true);
//		p4.setBorder(Orientation.NORTH, true);
//		p5.setBorder(Orientation.NORTH, true);
//		p5.setBorder(Orientation.SOUTH, true);
//		p8.setBorder(Orientation.NORTH, true);
//		one.add(p1, new Point(0, 0));
//		one.add(p2, new Point(1, 0));
//		one.add(p3, new Point(2, 0));
//		one.add(p4, new Point(0, 1));
//		one.add(p5, new Point(1, 1));
//		one.add(p6, new Point(2, 1));
//		one.add(p7, new Point(0, 2));
//		one.add(p8, new Point(1, 2));
//		one.add(p9, new Point(2, 2));
//		ShortestPathFinder finder = new DijkstraFinder(one);
//		Iterable<Point> r = finder.shortestPath(new Point(1, 2),
//				new Point(1, 0));
//		for (Point p : r)
//			System.out.println(p);
	}
	
	@Test
	public void panelTest2()
	{
		int size = 50;
		Board one = new Board(size,size);
		for(int i = 0; i < size;i++)
			for(int j=0;j<size;j++)
				one.add(new Panel(), new Point(i,j));
		ShortestPathFinderInterface f = new DijkstraFinder(one);
		Iterable<Point > points = f.shortestPath(new Point(0,0),new Point(3,3));
	for(Point p:	points)
		System.out.println(p);
	
		
	}
}
