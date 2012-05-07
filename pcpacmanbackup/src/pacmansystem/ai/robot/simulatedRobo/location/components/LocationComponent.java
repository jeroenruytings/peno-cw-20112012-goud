package pacmansystem.ai.robot.simulatedRobo.location.components;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobo.point.Pointf;

public abstract class LocationComponent
{
	private final int zIndex;
	protected LocationComponent(int zIndex)
	{
		this.zIndex=zIndex;
	}
	
	public abstract List<Pointf> getConvexPoints();
	public abstract void visit(LocationComponentVisitor visitor);
	public  int getZIndex(){
		return zIndex;
	}
}
