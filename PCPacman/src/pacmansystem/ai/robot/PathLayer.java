package pacmansystem.ai.robot;

import java.awt.Point;
import java.util.Iterator;



import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import util.board.Board;
import util.board.shortestpathfinder.dijkstra.DijkstraFinder;
import util.enums.Orientation;
import util.world.RobotData;

public class PathLayer {
	
	OrientationLayer orientationLayer;
	public OrientationLayer getOrientationLayer() {
		return orientationLayer;
	}

	private DijkstraFinder finder;
	private RobotData data;
	
	public PathLayer(RobotData data, OrientationLayer layer){
		orientationLayer = layer;
		finder = new DijkstraFinder(data.getBoard());
		this.data = data;
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
				Orientation o = Board.getOrientationBetween(currentPoint, nextPoint);
				orientationLayer.go(o);
				currentPoint = nextPoint;
				data.setPosition(currentPoint);
			} catch (IllegalDriveException e) {
				//fix things :D
				throw new IllegalDriveException(e);
			}
		}			
		}
	
	
}
