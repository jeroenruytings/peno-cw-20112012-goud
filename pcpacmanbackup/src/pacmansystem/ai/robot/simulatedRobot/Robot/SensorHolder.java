package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
/**
 * Class to hold the Ultrasonic sensor and the IR sensor.
 * *
 */
public class SensorHolder implements MovingComponent
{

	private UltrasonicSensor ultra;
	private IRSeekerV2 seeker;
	private MovingComponent moving;
	private int angle =0;
	public SensorHolder(Robot moving)
	{
		this.moving = moving;
		moving.setSensorHolder(this);
	}
	@Override
	public void tick(Ticker ticker)
	{
		
	}
	public void setUltraSonicSensor(UltrasonicSensor sensor)
	{
		this.ultra = sensor;
	}
	public void setIRSensor(IRSeekerV2 seeker)
	{
		this.seeker=seeker;
	}
	@Override
	public double getDirection()
	{
		
		return 0;
	}

	@Override
	public Pointf getLocation()
	{
		
		return null;
	}
	public UltrasonicSensor getUltraSonicSensor()
	{
		return ultra;
	}
	public IRSeekerV2 getIrSeekerV2()
	{
		return seeker;
	}
	public void rotate(int i)
	{
		// TODO Auto-generated method stub
		
	}
	
}
