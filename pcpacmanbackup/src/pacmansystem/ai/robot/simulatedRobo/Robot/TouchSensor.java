package pacmansystem.ai.robot.simulatedRobo.Robot;

import pacmansystem.ai.robot.simulatedRobo.ticking.Ticker;

public class TouchSensor extends Sensor
{

	public TouchSensor(Robot s4)
	{
		super(s4);
		s4.setTouch(this);
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
	public boolean hasChanged()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int importance()
	{
		// TODO Auto-generated method stub
		return 8;
	}

}
