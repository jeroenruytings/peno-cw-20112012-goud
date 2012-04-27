package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public interface Strategy {

	/**
	 * Constructs a route to follow. The route
	 * will be different depending on the strategy.
	 * @return	the route to follow
	 */
	public List<Point> constructRoute();

	/**
	 * Checks if the strategy has to be switched
	 */
	public boolean hasToSwitchStrategy();

	/**
	 * Returns the strategy to be switched to
	 * @return	the new strategy to follow
	 */
	public Strategy getReplacingStrategy();

	/**
	 * Checks if the robot has finished exploring the maze
	 */
	public boolean hasFinishedExploring();
	
	/**
	 * Checks if the robot has caught pacman
	 */
	public boolean hasCaughtPacman();
	
	/**
	 * Checks if the strategy has to update its plan, 
	 * but remain in the same strategy
	 */
	public boolean hasToUpdatePlan();
}
