package pacmansystem.ai.robot.simulatedRobo.location.components;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobo.point.Pointf;

public class OpenComponent extends NonMovingComponents
{

	private final int color;

	public OpenComponent(List<Pointf> points, int priority,int color)
	{
		super(points,priority);
		this.color=color;
	}
	public int getColor()
	{
		return color;
	}
	@Override
	public void visit(LocationComponentVisitor visitor)
	{
		visitor.visit(this);
	}




}
