package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pacmansystem.ai.RobotController;
import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;
import data.enums.Orientation;
import data.world.RobotData;

/**
 * This strategy hunts pacman down when the maze is fully constructed and pacman has
 * previously been seen somewhere on the board
 * @author Yuri
 *
 */
public class Hunt implements Strategy {
	
	private RobotController controller;
	private HashMap<Point, Double> pvalues;
	private boolean PathNotPossible;
	
	public Hunt(RobotController controller) {
		PathNotPossible = false;
		this.controller = controller;
		pvalues = new HashMap<Point, Double>(); 
		for(Point p : getController().getMergedBoard().getPanels().keySet()){
			pvalues.put(p,0.0);
		}
	}
	
	public RobotController getController() {
		return controller;
	}
	
	/**
	 * Constructs the route to follow. The robot will
	 * drive towards pacman according to collaborative
	 * diffusion:
	 * //http://scalablegamedesign.cs.colorado.edu/wiki/Collaborative_Diffusion
	 */
	@Override
	public List<Point> constructRoute() {
		updatePValues();
		Point destination = getBestDestination();
		DijkstraFinder finder = new DijkstraFinder(getController().getOwnData().getMergedBoard());
		List<Point> path = null;
		try {
			path = finder.shortestPath(getController().getCurrentPoint(), destination);
		} catch (PathNotPossibleException e) {
			PathNotPossible = true;
			return path;
		}
		path.remove(path.size()-1);
		return path;
	}
	
	private Point getBestDestination() {
		Point destination = null;
		double best = 0.0;
		for(Point p : pvalues.keySet()){
			double value = pvalues.get(p);
			if(value > best){
				best = value;
				destination = p;
			}
		}
		return destination;
	}

	@Override
	public boolean hasToSwitchStrategy() {
		if(PathNotPossible)
			return true;
		Point pacmanPos = getController().getOwnData().getPacmanLastSighted();
		Point currentPos = getController().getCurrentPoint();
		if(pacmanPos.distance(currentPos) == 1 && !getController().getOwnData().getBoard().wallBetween(pacmanPos, currentPos))
			return true;
		return false;
	}

	@Override
	public Strategy getReplacingStrategy() {
		if(PathNotPossible)
			return new Roam(getController());
		Point pacmanPos = getController().getOwnData().getPacmanLastSighted();
		Point currentPos = getController().getCurrentPoint();
		if(pacmanPos.distance(currentPos) == 1 && !getController().getOwnData().getBoard().wallBetween(pacmanPos, currentPos)) {
			getController().getOwnData().setPacman(null);
			getController().getOwnData().setLastChecked(new Date());
			return new Roam(controller);
		}
		return this;
	}

	@Override
	public boolean hasFinishedExploring() {
		return true;
	}
	
	//http://scalablegamedesign.cs.colorado.edu/wiki/Collaborative_Diffusion
	private void updatePValues(){
		resetPValues();
		Date bestDate = getController().getOwnData().getLastChecked();
		Point pacman = null;
		for (RobotData robot : getController().getWorld().get_robots().values()) {
			Date date = robot.getPacman().getDate();
			if (date.after(bestDate)) {
				bestDate = date;
				pacman = robot.getPacman().getPosition();
			}
		}
		ArrayList<Point> adapted = new ArrayList<Point>();
		pvalues.put(pacman, 1000.0);
		adapted.add(pacman);
		boolean updatedSomething = true;
		while(updatedSomething){
			updatedSomething = false;
			for(Point p : pvalues.keySet()){
				if(adapted.contains(p))
					continue;
				double newValue = calculateP(p);
				if(! (newValue == pvalues.get(p))){
					updatedSomething = true;
					adapted.add(p);
					pvalues.put(p, newValue);
				}
			}
		}
	}

	private void resetPValues() {
		for(Point p : pvalues.keySet()){
			pvalues.put(p, 0.0);
		}
	}

	private double calculateP(Point p) {
		double result = 0.0;
		ArrayList<Point> otherGhosts = new ArrayList<Point>();
		for(RobotData data : getController().getOtherBots()){
			otherGhosts.add(data.getPosition());
		}
		for(Orientation orientation : Orientation.values()){
			Point neighbourPoint = orientation.addTo(p);
			if(otherGhosts.contains(neighbourPoint))
				continue;
			if(pvalues.containsKey(neighbourPoint)){
				if(!getController().getMergedBoard().wallBetween(p,neighbourPoint))
					result += pvalues.get(neighbourPoint);
			}
		}
		return 0.25*result;
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
		return "HUNT";
	}
	
}
