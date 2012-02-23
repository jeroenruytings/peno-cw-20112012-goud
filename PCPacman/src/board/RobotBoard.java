package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import board.Panel.Orientation;

public class RobotBoard  {
	
	private int maxx;
	private int maxy;
	private Board relativeBoard;
	private Board absoluteBoard;
	private Point currentPositionRelative;
	private Collection<Point> possibleStartingPoints;
	private Map<Point,Point> absolutePointsFound;
	private Collection<Point> possibleStartingPoints()
	{
		Collection<Point> rv = new ArrayList<Point>();
		rv.add(new Point(0,0));
		rv.add(new Point(maxx,0));
		rv.add(new Point(0,maxy));
		rv.add(new Point(maxx,maxy));
		return rv;		
	}
	
	public RobotBoard(int maxx,int maxy)
	{
		this.maxx=maxx;
		this.maxy=maxy;
		this.relativeBoard = new Board();
		this.absoluteBoard = new Board();
		this.currentPositionRelative = new Point();
		this.absolutePointsFound = new HashMap<Point, Point>();
		possibleStartingPoints=possibleStartingPoints();
	}
	public void addAbsolutePanel(Point current,Point absolute)
	{
		
	}
	public void setCurrentPanel(Point xy,Panel panel)
	{
		relativeBoard.add(panel, xy);
		currentPositionRelative = xy;
		if(possibleStartingPoints.size()>1)
			fixPossibleStartingPoints();
		
	}
	
	private void fixPossibleStartingPoints() {
			if(onStartingPos())
			{
				removePosstibleStarts();
			}
	}

	private void removePosstibleStarts() {
		Panel startingpanel= this.relativeBoard.getPanelAt(currentPositionRelative);
		if(startingpanel.getBorder(Orientation.SOUTH)==false){
			possibleStartingPoints.remove(botLeft());
			possibleStartingPoints.remove(botRight());
		}
		if(startingpanel.getBorder(Orientation.NORTH)==false){
			possibleStartingPoints.remove(topLeft());
			possibleStartingPoints.remove(topRight());
		}
		if(startingpanel.getBorder(Orientation.WEST)==false){
			possibleStartingPoints.remove(topLeft());
			possibleStartingPoints.remove(botLeft());
		}
		if(startingpanel.getBorder(Orientation.EAST)==false){
			possibleStartingPoints.remove(topRight());
			possibleStartingPoints.remove(botRight());
		}
		
	}

	private Point botLeft() {
		return new Point(maxx,0);
	}
	private Point topLeft()
	{
		return new Point(0,0);
	}
	private Point topRight()
	{
		return new Point (0,maxy);
	}
	private Point botRight()
	{
		return new Point(maxx,maxy);
	}

	private boolean onStartingPos() {
		return currentPositionRelative.equals(new Point(0,0));
	}
	public boolean absoluteCoordsFound()
	{
		return possibleStartingPoints.size()==1;
			
	}

}
