package data.board.shortestpathfinder;

import java.awt.Point;

import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;

public interface ShortestPathFinderInterface
{

	Iterable<Point> shortestPath(Point point, Point point2) throws PathNotPossibleException;

}