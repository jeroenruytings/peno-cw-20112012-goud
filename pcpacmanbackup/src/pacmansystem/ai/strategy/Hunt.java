package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.Queue;

import pacmansystem.ai.RobotController;

/**
 * This strategy hunts pacman down when the maze is fully constructed and pacman has
 * previously been seen somewhere on the board
 * @author Yuri
 *
 */
public class Hunt implements Strategy {

	private RobotController controller;
	
	public Hunt(RobotController controller) {
		this.controller = controller;
	}
	
	public RobotController getController() {
		return controller;
	}
	
	@Override
	public Queue<Point> constructRoute() {
		//TODO
		return null;
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
	
}
