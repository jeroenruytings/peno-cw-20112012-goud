package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.Queue;

public interface Strategy {

	public Queue<Point> constructRoute();

	public boolean hasToSwitchStrategy();

	public Strategy getReplacingStrategy();

	public boolean hasFinishedExploring();
	
	public boolean hasCaughtPacman();
}
