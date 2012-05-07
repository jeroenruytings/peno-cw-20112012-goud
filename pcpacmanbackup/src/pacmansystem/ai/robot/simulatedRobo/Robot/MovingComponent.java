package pacmansystem.ai.robot.simulatedRobo.Robot;

import pacmansystem.ai.robot.simulatedRobo.point.Pointf;
import pacmansystem.ai.robot.simulatedRobo.ticking.Tickable;
public interface MovingComponent extends Tickable
{
	public double getDirection();
	public Pointf getLocation();
}
