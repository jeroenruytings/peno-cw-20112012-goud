package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

public class UltrasonicSensor
{

	private Robot robot;
	private MovingComponent moving;

	public UltrasonicSensor(Robot robot,MovingComponent moving)
	{
		this.robot=robot;
		this.moving =moving;
		}

	public int getDistance()
	{
		Pointf origin = getLocation();
		Pointf direction = getDirection();
		robot.getView().get(origin, direction);
		return 0;
	}
	private Pointf getDirection()
	{
		moving.getDirection();
		return null;
	}

	private Pointf getLocation()
	{
		Pointf here = moving.getLocation();
		return here;
	}
}
