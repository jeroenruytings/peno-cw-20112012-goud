package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.ticking.Tickable;

public interface Sensor extends Tickable
{

	public int readRawValue();
	public boolean hasChanged();

}
