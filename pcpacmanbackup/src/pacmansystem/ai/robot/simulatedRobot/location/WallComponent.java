package pacmansystem.ai.robot.simulatedRobot.location;

import java.util.List;

class WallComponent extends LocationComponent
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

