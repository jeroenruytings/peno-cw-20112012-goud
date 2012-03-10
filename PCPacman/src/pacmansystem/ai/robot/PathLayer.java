package pacmansystem.ai.robot;

import java.awt.Point;
import java.util.Iterator;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import util.board.Board;
import util.board.shortestpathfinder.dijkstra.DijkstraFinder;

public class PathLayer {
	
	OrientationLayer orientationLayer;
	public OrientationLayer getOrientationLayer() {
		return orientationLayer;
	}

	private DijkstraFinder finder;
	
	public PathLayer(Board board, OrientationLayer layer){
		orientationLayer = layer;
		finder = new DijkstraFinder(board);
	}
	
//	public PathLayer(Board board, RealWorld realworld){
//		directionLayer = new OrientationLayer(realworld);
//		finder = new ShortestPathFinder(board);
//	}
	
	public void go(Point start, Point end) throws IllegalDriveException{
		Iterable<Point> r = finder.shortestPath(start,end);
		Iterator<Point> s = r.iterator();
		Point currentPoint = s.next();
		while (s.hasNext()){
//			if(!s.hasNext())
//				break;
			Point nextPoint = s.next();
			try {
				orientationLayer.go(Board.getOrientationBetween(currentPoint, nextPoint));
				currentPoint = nextPoint;
			} catch (IllegalDriveException e) {
				//fix things :D
				throw new IllegalDriveException(e);
			}
		}			
		}
	
	
}
