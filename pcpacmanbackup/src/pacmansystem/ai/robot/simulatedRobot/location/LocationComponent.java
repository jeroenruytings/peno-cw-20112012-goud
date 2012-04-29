package pacmansystem.ai.robot.simulatedRobot.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class LocationComponent
{
	
	private List<Pointf> points_;
	private final int zIndex;
	LocationComponent(List<Pointf> points, int priority)
	{
		points_ = Collections.unmodifiableList(points);
		this.zIndex = priority;
	}
	public List<Pointf> getConvexPoints()
	{
		return new ArrayList<Pointf>(points_);
	}
	public abstract void visit(LocationComponentVisitor visitor);
	public int getPrior()
	{
		return  zIndex;
	}
}
