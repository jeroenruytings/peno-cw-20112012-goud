package pacmansystem.ai.robot.simulatedRobot.Robot;

import data.world.RealWorld;
import pacmansystem.ai.robot.simulatedRobot.location.RealWorldView;
import pacmansystem.ai.robot.simulatedRobot.location.RealWorldViewFromRealWorldObject;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

public class StandardRobotBuilder implements RobotBuilder
{
	private int speed;
	private RealWorldView view;
	private Pointf origin;
	private int degrees;
	/**
	 * 
	 */
	public StandardRobotBuilder()
	{
		
	}
	
	@Override
	public Robot build()
	{
		Ticker ticker = new Ticker();
		Robot r = new Robot(speed, view, origin, degrees);
		LightSensor lightSensor = new LightSensor(r);
		SensorHolder holder = new SensorHolder(r);
		UltrasonicSensor ultraSonicSensor = new UltrasonicSensor(r, holder);
		holder.setUltraSonicSensor(ultraSonicSensor);
		IRSeekerV2 seeker = new IRSeekerV2(r,holder);
		holder.setIRSensor(seeker);
		
		ticker.add(r);
		ticker.add(lightSensor);
		
		
		return r;
	}
	public void setRealWorld(RealWorld realworld)
	{
		view = new RealWorldViewFromRealWorldObject(realworld).build();
	}
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public void setView(RealWorldView view)
	{
		this.view = view;
	}

	public void setOrigin(Pointf origin)
	{
		this.origin = origin;
	}

	public void setDegrees(int degrees)
	{
		this.degrees = degrees;
	}
	
}
