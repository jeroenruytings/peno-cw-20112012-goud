package pacmansystem.ai.robot;

import java.awt.Point;
import java.util.Iterator;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import data.board.Board;
import data.board.Panel;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.enums.Orientation;
import data.world.OwnRobotData;

public class PathLayer {
	
	OrientationLayer orientationLayer;
	public OrientationLayer getOrientationLayer() {
		return orientationLayer;
	}

	private DijkstraFinder finder;
	private OwnRobotData data;
	
	public PathLayer(OwnRobotData data, OrientationLayer layer){
		orientationLayer = layer;
		finder = new DijkstraFinder(data);
		this.data = data;
	}
	
//	public PathLayer(Board board, RealWorld realworld){
//		directionLayer = new OrientationLayer(realworld);
//		finder = new ShortestPathFinder(board);
//	}
	@Deprecated
	/**
	 * Going more then one step at a time is kinda silly in our design...
	 * 
	 * */
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
				data.position(currentPoint);
				data.setOrientation(o);
			} catch (IllegalDriveException e) {
				//fix things :D
				throw new IllegalDriveException(e);
			}
		}			
		}
	
//	private String pointToString(Point p)
//	{
//		return p.x + "," + p.y;
//	}

	public Panel getPanel(Orientation currentOrientation)
	{
		Panel panel = orientationLayer.getPanel(currentOrientation);
		data.discover(data.getPosition(), panel);
		return panel;
	}

	public void goOneStep(Point currentPoint, Point destination) throws IllegalDriveException
	{
		Iterable<Point> r;
		try{
		r = finder.shortestPath(currentPoint,destination);
		}catch(NullPointerException nullp){
			throw new IllegalDriveException();
			
		}
		Iterator<Point> s = r.iterator();
		Point current = s.next();
		Point next = s.next();
		Orientation o;
		try {
			
			o = Board.getOrientationBetween(current,next);
			orientationLayer.go(o);
		} catch (IllegalDriveException e) {
			System.out.println("exceptoin");
			throw new IllegalDriveException();
		
		}

		data.position(next);
		data.setOrientation(o);
	}
}
