package pacmansystem.ai.robot.simulatedRobo.Robot;

import pacmansystem.ai.robot.simulatedRobo.ticking.Tickable;

public abstract class Sensor implements Tickable
{
	protected final Robot robot;
	public Sensor(Robot robot)
	{
		this.robot =robot;
	}
	public abstract boolean hasChanged();

}
