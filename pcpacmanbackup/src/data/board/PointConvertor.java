package data.board;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import data.board.operations.Operations;
import data.board.operations.Operations.Turn;
import data.enums.Direction;
import data.enums.Orientation;




public class PointConvertor {
	
	private double xPlus;
	private double yPlus;
	private Map<Orientation,Orientation> orientations = new HashMap<Orientation, Orientation>();
	private Map<Orientation,Orientation> orientationsInverse = new HashMap<Orientation, Orientation>();
	private Point _vec;
	private int _lt;
	private Point _two;

	public Map<Orientation, Orientation> getOrientationsInverse() {
		return orientationsInverse;
	}

	public PointConvertor(Point commonPointFromFirst, Point commonPointFromSecond, Map<Orientation,Orientation> orientations){
		this(commonPointFromFirst,commonPointFromSecond,Orientation.NORTH,orientations.get(Orientation.NORTH));
		//		this.orientations = orientations;
//		Point secondPointConverted = convertOrientations(commonPointFromSecond);
//		xPlus = commonPointFromFirst.getX() - secondPointConverted.getX();
//		yPlus = commonPointFromFirst.getY() - secondPointConverted.getY();
//		this.orientations = orientations;
		for(Orientation orientation:orientations.values()){
			orientationsInverse.put(orientations.get(orientation), orientation);
		}
	}
	public PointConvertor(Point one,Point two,Orientation o1,Orientation o2)
	{
		Point vector = Operations.min(one, two);
		_two=two;
		int leftTurns = 0;
		Orientation start = o1;
		while(!start.equals(o2)){
			start=start.addTo(Direction.LEFT);
			leftTurns++;
		}
		_vec = vector;
		_lt =leftTurns;
		
	}
	public static void main(String[] args) {
		PointConvertor c = new PointConvertor(new Point(1,1),new Point(5,5),Orientation.NORTH, Orientation.WEST);
		System.out.println(c.convert2(new Point(5,6)));
	}

	private Point convert2(Point point) {
		Point rv = point;
		for(int i =0;i<_lt;i++)
			rv=Operations.turn(rv,_two, Turn.LEFT);
		return Operations.translate(rv, _vec);
		
	}

	private Point convertOrientations(Point secondPointConverted) {
		if(orientations.get(Orientation.NORTH).equals(Orientation.WEST)){
			secondPointConverted = new Point((int)secondPointConverted.getY(),-(int)secondPointConverted.getX());
		}
		if(orientations.get(Orientation.NORTH).equals(Orientation.EAST)){
			secondPointConverted = new Point(-(int)secondPointConverted.getY(),(int)secondPointConverted.getX());
		}
		if(orientations.get(Orientation.NORTH).equals(Orientation.NORTH)){
			secondPointConverted = new Point((int)secondPointConverted.getX(),(int)secondPointConverted.getY());
		}
		if(orientations.get(Orientation.NORTH).equals(Orientation.SOUTH)){
			secondPointConverted = new Point(-(int)secondPointConverted.getX(),-(int)secondPointConverted.getY());
		}
		return secondPointConverted;
	}
	
	public Map<Orientation, Orientation> getOrientations() {
		return orientations;
	}


	public Point convert(Point point){
		return convert2(point);
//		Point convertedPoint = convertOrientations(point);
//		//Point convertedPoint = point;
//		return new Point((int)(convertedPoint.getX()+xPlus),(int)(convertedPoint.getY()+yPlus));
	}

}
