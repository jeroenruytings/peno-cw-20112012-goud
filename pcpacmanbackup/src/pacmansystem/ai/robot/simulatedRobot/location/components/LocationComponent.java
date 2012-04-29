package pacmansystem.ai.robot.simulatedRobot.location.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

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
