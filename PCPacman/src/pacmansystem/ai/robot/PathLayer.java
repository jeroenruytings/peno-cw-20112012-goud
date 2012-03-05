package pacmansystem.ai.robot;

import java.awt.Point;
import java.util.Iterator;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import pacmansystem.board.Board;
import pacmansystem.board.ShortestPathFinder;
import pacmansystem.world.RealWorld;

public class PathLayer {
	
	DirectionLayer directionLayer;
	public DirectionLayer getDirectionLayer() {
		return directionLayer;
	}

	private ShortestPathFinder finder;
	
	public PathLayer(Board board){
		directionLayer = new DirectionLayer();
		finder = new ShortestPathFinder(board);
	}
	
	public PathLayer(Board board, RealWorld realworld){
		directionLayer = new DirectionLayer(realworld);
		finder = new ShortestPathFinder(board);
	}
	
	public void go(Point start, Point end){
		Iterable<Point> r = finder.shortestPath(start,end);
		Iterator<Point> s = r.iterator();
		Point currentPoint = s.next();
		while (s.hasNext()){
			if(!s.hasNext())
				break;
			Point nextPoint = s.next();
			try {
				directionLayer.go(Board.getOrientationBetween(currentPoint, nextPoint));
				currentPoint = nextPoint;
			} catch (IllegalDriveException e) {
				//Dit kan nooit gebeuren
			}
		}			
		}
	
	
}
