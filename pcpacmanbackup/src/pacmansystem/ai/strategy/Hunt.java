package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import data.board.shortestpathfinder.dijkstra.DijkstraFinder;
import data.board.shortestpathfinder.dijkstra.PathNotPossibleException;
import data.enums.Direction;
import data.enums.Orientation;
import data.world.RobotData;

import pacmansystem.ai.RobotController;

/**
 * This strategy hunts pacman down when the maze is fully constructed and pacman has
 * previously been seen somewhere on the board
 * @author Yuri
 *
 */
public class Hunt implements Strategy {
	
	private RobotController controller;
	private HashMap<Point, Double> pvalues;
	
	public Hunt(RobotController controller) {
		this.controller = controller;
		pvalues = new HashMap<Point, Double>(); 
		for(Point p : getController().getBoard().getPanels().keySet()){
			pvalues.put(p,0.0);
		}
	}
	
	public RobotController getController() {
		return controller;
	}
	
	@Override
	public Queue<Point> constructRoute() {
		updatePValues();
		Point destination = null;
		double best = 0.0;
		for(Point p : pvalues.keySet()){
			double value = pvalues.get(p);
			if(value > best){
				best = value;
				destination = p;
			}
		}
		DijkstraFinder finder = new DijkstraFinder(getController().getData());
		List<Point> path = null;
		try {
			path = finder.shortestPath(getController().getCurrentPoint(), destination);
		} catch (PathNotPossibleException e) {
			e.printStackTrace();
		}
		path.remove(path.size()-1);
		Queue<Point> route = new LinkedList<Point>();
		for (Point point: path)
			route.add(point);
		return route;
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
		return true;
	}
	
	//http://scalablegamedesign.cs.colorado.edu/wiki/Collaborative_Diffusion
	private void updatePValues(){
		resetPValues();
		Point pacman = getController().getOwnData().getPacmanLastSighted();
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
			Point point = orientation.addTo(p);
			if(otherGhosts.contains(point))
				continue;
			if(pvalues.containsKey(point)){
				if(!getController().getBoard().wallBetween(p,point))
					result += pvalues.get(point);
			}
		}
		return 0.25*result;
	}
	
}
