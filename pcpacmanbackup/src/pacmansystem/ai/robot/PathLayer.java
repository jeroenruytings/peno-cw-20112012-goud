package pacmansystem.ai.robot;

import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import util.board.Board;
import util.board.Panel;
import util.board.shortestpathfinder.dijkstra.DijkstraFinder;
import util.enums.Orientation;
import util.world.OwnRobotData;
import util.world.RobotData;

import communicator.be.kuleuven.cs.peno.MessageSender;

public class PathLayer {
	
	OrientationLayer orientationLayer;
	public OrientationLayer getOrientationLayer() {
		return orientationLayer;
	}

	private DijkstraFinder finder;
	private OwnRobotData data;
	
	public PathLayer(OwnRobotData data, OrientationLayer layer){
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
				data.position(currentPoint);
				data.setOrientation(o);
			} catch (IllegalDriveException e) {
				//fix things :D
				throw new IllegalDriveException(e);
			}
		}			
		}
	
	private String pointToString(Point p)
	{
		return p.x + "," + p.y;
	}

	public Panel getPanel(Orientation currentOrientation)
	{
		Panel panel = orientationLayer.getPanel(currentOrientation);
		data.discover(data.getPosition(), panel);
		return panel;
	}
	
	
}
