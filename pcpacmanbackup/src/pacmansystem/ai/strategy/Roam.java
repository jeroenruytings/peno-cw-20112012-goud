package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pacmansystem.ai.RobotController;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;
import data.world.RobotData;

/**
 * This strategy roams around the board when it is fully constructed and pacman has
 * not yet been seen.
 * @author Yuri
 *
 */
public class Roam implements Strategy {

	private RobotController controller;
	
	private Set<Point> visitedPoints;
	
	public Roam(RobotController controller) {
		this.controller = controller;
		visitedPoints = new HashSet<Point>();
	}
	
	public RobotController getController() {
		return controller;
	}
	
	/**
	 * Returns the route to follow. A random point that does
	 * not belong to another robot's path is chosen. The robot
	 * then drives to this point.
	 */
	@Override
	public List<Point> constructRoute() {
		Point randomPoint = getRandomPoint();
		List<Point> plan = new LinkedList<Point>();
		DijkstraFinder finder = new DijkstraFinder(getController().getOwnData().getMergedBoard());
		Iterator<Point> path = null;
		try {
			path = finder.shortestPath(getController().getCurrentPoint(), randomPoint).iterator();
			//TODO: path niet korste pad, maar zo weinig mogelijk overlappend
		} catch (PathNotPossibleException e) {
			return constructRoute();
		}
		while (path.hasNext()){
			plan.add(path.next());
		}
		return plan;
	}
	
	/**
	 * Returns a random point from the robot's own board
	 */
	private Point getRandomPoint() {
		Set<Point> unVisitedPoints = getUnVisitedPoints();
		Point[] pointsArray = new Point[unVisitedPoints.size()];
		int i = 0;
		for (Point point: unVisitedPoints) {
			pointsArray[i] = point;
			i++;
		}
		boolean conflictingPoint = true;
		Point randomPoint = null;
		while(conflictingPoint){
			conflictingPoint = false;
			int randomNumber = (int) (Math.random() * pointsArray.length);
			randomPoint = pointsArray[randomNumber];
			for(RobotData data : getController().getOtherBots()){
				if(data.getRemainingPlan().contains(randomPoint)){
					conflictingPoint = true;
				}
			}
		}
		visitedPoints.add(randomPoint);
		return randomPoint;
	}
	
	private Set<Point> getUnVisitedPoints() {
		Set<Point> allPoints = getController().getMergedBoard().getPanels().keySet();
		if (visitedPoints.size() == allPoints.size())
			visitedPoints = new TreeSet<Point>();
		for (Point point : visitedPoints) {
			allPoints.remove(point);
		}
		return allPoints;
	}

	@Override
	public boolean hasToSwitchStrategy() {
		if(getController().getOwnData().foundMistakes())
			return true;
		if(getController().pacmanRecentlySeenAndReachable())
			return true;
		return false;
	}

	@Override
	public Strategy getReplacingStrategy() {
		if(getController().getOwnData().foundMistakes())
			return new Explore(getController());
		if(getController().pacmanRecentlySeenAndReachable())
			return new Hunt(getController());
		return this;
	}

	/**
	 * Returns false. The robot SHOULD be done exploring in this case, but
	 * there might have been an error. Returning false allows the possibility
	 * to fix that error.
	 */
	@Override
	public boolean hasFinishedExploring() {
		return false;
	}

	@Override
	public boolean hasCaughtPacman() {
		return false;
	}

	@Override
	public boolean hasToUpdatePlan() {
		return false;
	}
	
	@Override
	public String toString() {
		return "ROAM";
	}

}
