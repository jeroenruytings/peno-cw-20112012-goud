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
	public SensorHolder(MovingComponent moving)
	{
		this.moving = moving;
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
	public int getDirection()
	{
		
		return 0;
	}

	@Override
	public Pointf getLocation()
	{
		
		return null;
	}
	
}
