package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.ticking.Tickable;

public abstract class Sensor implements Tickable
{
	protected final Robot robot;
	public Sensor(Robot robot)
	{
		this.robot =robot;
	}
	public abstract boolean hasChanged();

}
