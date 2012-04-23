package pacmansystem.ai.strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public interface Strategy {

	public List<Point> constructRoute();

	public boolean hasToSwitchStrategy();

	public Strategy getReplacingStrategy();

	public boolean hasFinishedExploring();
	
	public boolean hasCaughtPacman();
	
	public boolean hasToUpdatePlan();
}
