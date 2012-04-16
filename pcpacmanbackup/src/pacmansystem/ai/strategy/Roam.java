package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.Queue;
import java.util.Set;

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
		do {
		Set<Point> allPoints = getController().getBoard().getPanels().keySet();
		Point[] pointsArray = new Point[allPoints.size()];
		int i = 0;
		for (Point point: allPoints) {
			pointsArray[i] = point;
			i++;
		}
		int randomNumber = (int) (Math.random() * allPoints.size());
		Point randomPoint = pointsArray[randomNumber];
		} while (); //while (randomPoint behoort niet tot iemand anders zijn pad)
		//TODO: fix the red
		
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
