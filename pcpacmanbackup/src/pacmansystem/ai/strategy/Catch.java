package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pacmansystem.ai.RobotController;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;

/**
 * This strategy attempts to catch pacman while the maze is not yet fully constructed
 * @author Yuri
 *
 */
public class Catch implements Strategy {
	
	private RobotController controller;
	private boolean pathNotPossible;
	
	public Catch(RobotController controller) {
		this.controller = controller;
		pathNotPossible = false;
	}
	
	public RobotController getController() {
		return controller;
	}

	/**
	 * Returns the route to follow.
	 * The route is a queue of points (shortest path) from the current position to
	 * pacman, with the position of pacman removed from the queue.
	 */
	@Override
	public List<Point> constructRoute() {
		Point destination = getController().getOwnData().getPacmanLastSighted();
		DijkstraFinder finder = new DijkstraFinder(getController().getOwnData().getMergedBoard());
		List<Point> path = null;
		try {
			path = finder.shortestPath(getController().getCurrentPoint(), destination);
		} catch (PathNotPossibleException e) {
			pathNotPossible = true;
			return path;
		}
		path.remove(path.size()-1);
		List<Point> route = new LinkedList<Point>();
		for (Point point: path)
			route.add(point);
		return route;
	}

	@Override
	public boolean hasToSwitchStrategy() {
		if(pathNotPossible)
			return true;
		if(getController().getOwnData().foundMistakes())
			return true;
		Point pacmanPos = getController().getOwnData().getPacmanLastSighted();
		Point currentPos = getController().getCurrentPoint();
		if(pacmanPos.distance(currentPos) == 1 && !getController().getOwnData().getBoard().wallBetween(pacmanPos, currentPos))
			return true;
		return false;
	}

	@Override
	public Strategy getReplacingStrategy() {
		if(pathNotPossible)
			return new Explore(getController());
		if(getController().getOwnData().foundMistakes())
			return new Explore(getController());
		Point pacmanPos = getController().getOwnData().getPacmanLastSighted();
		Point currentPos = getController().getCurrentPoint();
		if(pacmanPos.distance(currentPos) == 1 && !getController().getOwnData().getBoard().wallBetween(pacmanPos, currentPos)) {
			getController().getOwnData().setPacman(null);
			getController().getOwnData().setLastChecked(new Date());
			return new Explore(getController());
		}
		return this;
	}

	@Override
	public boolean hasFinishedExploring() {
		return false;
	}

	@Override
	public boolean hasCaughtPacman() {
		return !getController().pacmanCanMoveToOtherPanel();
	}

	@Override
	public boolean hasToUpdatePlan() {
		Point pacmanPos = getController().getOwnData().getPacmanLastSighted();
		if (getController().getOwnData().getRemainingPlan().contains(pacmanPos))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "CATCH";
	}

}
