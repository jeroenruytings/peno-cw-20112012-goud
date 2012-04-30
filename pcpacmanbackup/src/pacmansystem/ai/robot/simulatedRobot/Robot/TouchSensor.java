package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

public class TouchSensor extends Sensor
{

	public TouchSensor(Robot s4)
	{
		super(s4);
	}

	public boolean isPressed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void tick(Ticker ticker)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int readRawValue()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasChanged()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
