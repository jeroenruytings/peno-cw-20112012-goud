package pacmansystem.ai.robot.simulatedRobot.location;

import java.util.List;

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
