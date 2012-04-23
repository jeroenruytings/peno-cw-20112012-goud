package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
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
	
	@Override
	public Queue<Point> constructRoute() {
		Point randomPoint = getRandomPoint();
		Queue<Point> plan = new LinkedList<Point>();
		DijkstraFinder finder = new DijkstraFinder(getController().getData());
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
	
	private Point getRandomPoint() {
		Set<Point> allPoints = getController().getBoard().getPanels().keySet();
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
			int randomNumber = (int) (Math.random() * allPoints.size());
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
		if(getController().getOwnData().getPacmanLastSighted() != null)
			return true;
		return false;
	}

	@Override
	public Strategy getReplacingStrategy() {
		if(getController().getOwnData().getPacmanLastSighted() != null)
			return new Hunt(getController());
		return this;
	}

	/**
	 * Returns false. The robot SHOULD be done exploring in this case, but
	 * because of an error, it might not.
	 */
	@Override
	public boolean hasFinishedExploring() {
		return false;
	}

	@Override
	public boolean hasCaughtPacman() {
		return false;
	}

}
