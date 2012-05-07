package pacmansystem.ai.robot.simulatedRobo.location.components;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobo.point.Pointf;

public class WallComponent extends NonMovingComponents
{
	
	public WallComponent(List<Pointf> points, int priority)
	{
		super(points,priority);
	}

	@Override
	public void visit(LocationComponentVisitor visitor)
	{
		visitor.visit(this);
	}

	
}

