package util.board;

import java.awt.Point;
import java.util.Map;

import util.enums.Orientation;



public class PointConvertor {
	
	private double xPlus;
	private double yPlus;
	private Map<Orientation,Orientation> orientations;

	public PointConvertor(Point commonPointFromFirst, Point commonPointFromSecond, Map<Orientation,Orientation> orientations){
		Point secondPointConverted = commonPointFromSecond;
		if(orientations.get(Orientation.NORTH).equals(Orientation.WEST)){
			secondPointConverted = new Point((int)secondPointConverted.getY(),(int)secondPointConverted.getX());
		}
		if(orientations.get(Orientation.NORTH).equals(Orientation.EAST)){
			secondPointConverted = new Point((int)secondPointConverted.getY(),(int)-secondPointConverted.getX());
		}
		if(orientations.get(Orientation.NORTH).equals(Orientation.NORTH)){
			secondPointConverted = new Point((int)secondPointConverted.getX(),-(int)secondPointConverted.getY());
		}
		if(orientations.get(Orientation.NORTH).equals(Orientation.SOUTH)){
			secondPointConverted = new Point((int)-secondPointConverted.getX(),(int)secondPointConverted.getY());
		}
		
		xPlus = commonPointFromFirst.getX() + secondPointConverted.getX();
		yPlus = commonPointFromFirst.getY() + secondPointConverted.getY();
		this.orientations = orientations;
	}
	
	public Map<Orientation, Orientation> getOrientations() {
		return orientations;
	}

	public Point convert(Point point){
		return new Point((int)(point.getX()+xPlus),(int)(point.getY()+yPlus));
	}
}
