package pacmansystem.ai.robot;

import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;

import communicator.be.kuleuven.cs.peno.MessageSender;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import util.board.Board;
import util.board.shortestpathfinder.dijkstra.DijkstraFinder;
import util.enums.Orientation;
import util.world.RobotData;
import util.world.RobotData2;

public class PathLayer extends Layer {
	
	OrientationLayer orientationLayer;
	public OrientationLayer getOrientationLayer() {
		return orientationLayer;
	}

	private DijkstraFinder finder;
	
	
	
	public PathLayer(OrientationLayer orientationLayer2,RobotData2 data)  {
		super(data);
		orientationLayer = orientationLayer2;	
		
	}

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
				_data.setPosition(currentPoint);
				try{
					MessageSender.getInstance().sendMessage(
							 _data.getName()
							+ " POSITION "
							+ pointToString(currentPoint)
							+ "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				_data.setOrientation(o);
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
	
	
}
