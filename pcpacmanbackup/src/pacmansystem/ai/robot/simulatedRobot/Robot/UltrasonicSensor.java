package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

public class UltrasonicSensor extends Sensor
{

	private MovingComponent moving;

	public UltrasonicSensor(Robot robot,MovingComponent moving)
	{
		super(robot);
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

	@Override
	public void tick(Ticker ticker)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasChanged()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
