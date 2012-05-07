package pacmansystem.ai.robot.simulatedRobo.Robot;

import pacmansystem.ai.robot.simulatedRobo.ticking.Ticker;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

public class IRSeekerV2 extends Sensor
{
	public static enum Mode
	{
		AC, DC
	};

	public static final byte ADDRESS2 = 0x8;
	byte[] buf = new byte[1];
	public static final float noAngle = Float.NaN;

	private Mode mode;
	private MovingComponent holder;

	public IRSeekerV2(Robot r, SensorHolder holder)
	{
		super(r);
		this.holder = holder;
	}

	/**
	 * Set the mode of the sensor
	 */
	public void setMode(Mode mode)
	{
		this.mode = mode;
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
