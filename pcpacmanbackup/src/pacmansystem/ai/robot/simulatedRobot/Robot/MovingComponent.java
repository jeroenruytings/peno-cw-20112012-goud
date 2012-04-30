package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Tickable;
public interface MovingComponent extends Tickable
{
	public int getDirection();
	public Pointf getLocation();
}
