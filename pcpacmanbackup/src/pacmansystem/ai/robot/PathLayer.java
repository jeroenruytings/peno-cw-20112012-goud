package pacmansystem.ai.robot;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import pacmansystem.ai.robot.fysicalRobot.connector.CrashedException;
import pacmansystem.ai.robot.simulatedRobo.IllegalDriveException;
import data.board.Board;
import data.board.Panel;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;
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
		finder = new DijkstraFinder(data.getMergedBoard());
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
	public void go(Point start, Point end) throws IllegalDriveException, CrashedException {
		Iterable<Point> r = null;
		try {
			r = finder.shortestPath(start, end);
		} catch (PathNotPossibleException e1) {
			e1.printStackTrace();
		}
		Iterator<Point> s = r.iterator();
		Point currentPoint = s.next();
		while (s.hasNext()) {
			// if(!s.hasNext())
			// break;
			Point nextPoint = s.next();
			try {
				Orientation o = Board.getOrientationBetween(currentPoint,
						nextPoint);
				orientationLayer.go(o);
				currentPoint = nextPoint;
				data.position(currentPoint);
				data.setOrientation(o);
			} catch (IllegalDriveException e) {
				// fix things :D
				throw new IllegalDriveException(e);
			}
		}
	}

//	private String pointToString(Point p)
//	{
//		return p.x + "," + p.y;
//	}

	public Panel getPanel()
	{
		Panel panel = orientationLayer.getPanel();
		data.discover(data.getPosition(), panel);
		return panel;
	}

	public void goOneStep(Point currentPoint, Point destination) throws IllegalDriveException, CrashedException
	{
		List<Point> r;
		try{
		r = finder.shortestPath(currentPoint,destination);
		}catch(PathNotPossibleException e){
			throw new IllegalDriveException();
			
		}
		//Iterator<Point> s = r.iterator();
		Point current = r.get(0);
		Point next = r.get(1);
		Orientation o =null;
		try {
			
			o = Board.getOrientationBetween(current,next);
			orientationLayer.go(o);
			data.position(next);
			data.setOrientation(o);
		} catch (IllegalDriveException e) {
			e.printStackTrace();
		
		}
	}
}
