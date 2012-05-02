package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pacmansystem.ai.RobotController;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;
import data.enums.Orientation;


/**
 * This strategy explores the maze
 * @author Yuri
 *
 */
public class Explore implements Strategy {
	
	private RobotController controller;
	private boolean finishedExploring;
	private boolean pathNotPossible;
	
	public Explore(RobotController controller) {
		pathNotPossible = false;
		this.controller = controller;
		controller.getOwnData().foundMistake(false);
		finishedExploring = false;
	}
	
	public RobotController getController() {
		return controller;
	}
	
	/**
	 * Returns the route to follow. Returns an empty plan if the robot has finished exploring.
	 */
	@Override
	public List<Point> constructRoute()
	{
		Point destination = null;
		Orientation orientation = nextMove();
		if (orientation == null){
			destination = searchNext();
		}
		else
			destination = orientation.addTo(getController().getCurrentPoint());
		List<Point> plan = new LinkedList<Point>();
		if(destination != null){
			DijkstraFinder finder = new DijkstraFinder(getController().getOwnData().getMergedBoard());
			Iterator<Point> path = null;
			try {
				path = finder.shortestPath(getController().getCurrentPoint(), destination).iterator();
			} catch (PathNotPossibleException e) {
				pathNotPossible = true;
				return plan;
			}
			while (path.hasNext()){
				plan.add(path.next());
			}
			return plan;
		}
		else{
			finishedExploring = true;
			return plan;
			//TODO: plan = null hier!
		}
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
			if (getController().getMergedBoard().wallBetween(position, orientation))
				continue;
			if(getController().getMergedBoard().hasPanelAt(orientation.addTo(getController().getCurrentPoint())))
				continue;
			Point possibleDest = orientation.addTo(getController().getCurrentPoint());
			int temp = getController().getMergedBoard().nbOfUnknowns(possibleDest);
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
		for (Point point : getController().getMergedBoard().getPanels().keySet()) {
			if (point.equals(getController().getCurrentPoint()))
				continue;
			int nbKnown = 4 - getController().getMergedBoard().nbOfUnknowns(point);
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
		if(pathNotPossible)
			return true;
		if(getController().getOwnData().foundMistakes())
			return true;
		if(hasFinishedExploring())
			return true;
		if(!hasFinishedExploring() && getController().getOwnData().getPacmanLastSighted() != null)
			return true;
		return false;
	}

	@Override
	public Strategy getReplacingStrategy() {
		if(pathNotPossible)
			return new Roam(getController());
		if(getController().getOwnData().foundMistakes())
			return new Explore(getController());
		if(hasFinishedExploring() && getController().pacmanRecentlySeenAndReachable())
			return new Hunt(getController());
		if(hasFinishedExploring() && !getController().pacmanRecentlySeenAndReachable())
			return new Roam(getController());
		if(!hasFinishedExploring() && getController().getOwnData().getPacmanLastSighted() != null) //TODO: willen we mss dat hij ook catch als hij bereikbaar is, maar gezien door iemand anders?
			return new Catch(getController());
		return this;
	}

	@Override
	public boolean hasFinishedExploring() {
		return finishedExploring;
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
		return "EXPLORE";
	}

}
