package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;
import data.enums.Orientation;

import pacmansystem.ai.RobotController;


/**
 * This strategy explores the maze
 * @author Yuri
 *
 */
public class Explore implements Strategy {
	
	private RobotController controller;
	
	public Explore(RobotController controller) {
		this.controller = controller;
	}
	
	public RobotController getController() {
		return controller;
	}
	
	@Override
	public Queue<Point> constructRoute()
	{
		Point destination = null;
		Orientation orientation = nextMove();
		if (orientation == null)
			destination = searchNext();
		else
			destination = orientation.addTo(getController().getCurrentPoint());
		Queue<Point> plan = new LinkedList<Point>();
		DijkstraFinder finder = new DijkstraFinder(getController().getData());
		Iterator<Point> path = null;
		try {
			path = finder.shortestPath(getController().getCurrentPoint(), destination).iterator();
		} catch (PathNotPossibleException e) {
			e.printStackTrace();
		}
		while (path.hasNext()){
			plan.add(path.next());
		}
		return plan;
	}
	
	/**
	 * 
	 * @return de ori�ntatie waar je naartoe moet.
	 * @return null als alle omliggende vakjes gekend zijn.
	 */
	public Orientation nextMove()
	{
		Point position = getController().getCurrentPoint();
		Orientation best = null;
		int nbUnknowns = 0;
		for (Orientation orientation : Orientation.values()) {
			if (getController().getBoard().wallBetween(position, orientation))
				continue;
			if(getController().getBoard().hasPanelAt(orientation.addTo(getController().getCurrentPoint())))
				continue;
			Point possibleDest = orientation.addTo(getController().getCurrentPoint());
			int temp = getController().getBoard().nbOfUnknowns(possibleDest);
			if (temp >= nbUnknowns) {
				best = orientation;
				nbUnknowns = temp;
			}
		}
		return best;
	}
	
	/**
	 * 
	 * @return het beste punt om naar toe te rijden
	 * @return null als alle punten gekend zijn.
	 */
	private Point searchNext()
	{
		Point best = null;
		int waarde = 1000;
		for (Point point : getController().getBoard().getPanels().keySet()) {
			if (point.equals(getController().getCurrentPoint()))
				continue;
			int nbKnown = 4 - getController().getBoard().nbOfUnknowns(point);
			if (nbKnown == 4)
				continue;
			int temp = nbKnown + heuristiek(point);
			if (temp < waarde) {
				best = point;
				waarde = temp;
			}
		}
		return best;
	}
	
	private int heuristiek(Point destination)
	{
		return (int) (Math.abs(destination.getX() - getController().getCurrentPoint().getX()) + Math
				.abs(destination.getY() - getController().getCurrentPoint().getY()));
	}

	@Override
	public boolean hasToSwitchStrategy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Strategy getReplacingStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasFinishedExploring() {
		return false;
	}
}
