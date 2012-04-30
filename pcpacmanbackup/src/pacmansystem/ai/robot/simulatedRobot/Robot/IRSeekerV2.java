package pacmansystem.ai.robot.simulatedRobot.Robot;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */

public class IRSeekerV2
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
	private Robot robot;

	public IRSeekerV2(Robot r, SensorHolder holder)
	{
		this.robot = r;
		this.holder = holder;
	}

	/**
	 * Set the mode of the sensor
	 */
	public void setMode(Mode mode)
	{
		this.mode = mode;
	}

}
