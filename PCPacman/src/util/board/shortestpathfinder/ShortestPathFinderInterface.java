package util.board.shortestpathfinder;

import java.awt.Point;

public interface ShortestPathFinderInterface
{

	Iterable<Point> shortestPath(Point point, Point point2);

}