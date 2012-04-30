package pacmansystem.ai.robot.simulatedRobot.location.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

public abstract class NonMovingComponents extends LocationComponent
{
	private final int zIndex;
	private List<Pointf> convexPoints;
	public NonMovingComponents(List<Pointf> convexPoints, int zIndex)
	{
		super(zIndex);
		this.zIndex = zIndex;
		convexPoints = Collections.unmodifiableList(convexPoints);
	}
	public int getZIndex()
	{
		return zIndex;
	}
	@Override
	public List<Pointf> getConvexPoints()
	{
		return new ArrayList<Pointf>(convexPoints);
	}
}
