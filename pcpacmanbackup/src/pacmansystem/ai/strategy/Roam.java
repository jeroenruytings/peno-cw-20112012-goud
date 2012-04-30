package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;
import data.world.RobotData;

import pacmansystem.ai.RobotController;

/**
 * This strategy roams around the board when it is fully constructed and pacman has
 * not yet been seen.
 * @author Yuri
 *
 */
public class Roam implements Strategy {

	private RobotController controller;
	
	public Roam(RobotController controller) {
		this.controller = controller;
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
			e.printStackTrace();
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
		Set<Point> allPoints = getController().getMergedBoard().getPanels().keySet();
		Point[] pointsArray = new Point[allPoints.size()];
		int i = 0;
		for (Point point: allPoints) {
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
		return randomPoint;
	}

	@Override
	public boolean hasToSwitchStrategy() {
		if(getController().getOwnData().foundMistakes())
			return true;
		if(getController().getOwnData().getPacmanLastSighted() != null)
			return true;
		return false;
	}

	@Override
	public Strategy getReplacingStrategy() {
		if(getController().getOwnData().foundMistakes())
			return new Explore(getController());
		if(getController().getOwnData().getPacmanLastSighted() != null)
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

}
