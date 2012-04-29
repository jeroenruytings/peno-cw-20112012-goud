package pacmansystem.ai.robot.simulatedRobot.location.components;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

public class OpenComponent extends LocationComponent
{

	public OpenComponent(List<Pointf> points, int priority)
	{
		super(points,priority);
	}

	@Override
	public void visit(LocationComponentVisitor visitor)
	{
		visitor.visit(this);
	}




}
