package pacmansystem.ai.robot.simulatedRobot.Robot;

import java.awt.Point;

import pacmansystem.ai.robot.simulatedRobot.SIMINFO;
import pacmansystem.ai.robot.simulatedRobot.location.RealWorldView;
import pacmansystem.ai.robot.simulatedRobot.location.RealWorldViewFromRealWorldObject;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
import data.world.RealWorld;

public class StandardRobotBuilder implements RobotBuilder
{
	private int speed;
	private RealWorldView view;
	private Pointf origin;
	private int degrees;
	private Ticker ticker;
	private boolean build=false;
	/**
	 * 
	 */
	public StandardRobotBuilder()
	{
		
	}
	/**
	 * Builds a robot object & all the sensors.
	 * attaches the ticker to all components.
	 */
	@Override
	public Robot build()
	{
		if(build)
			throw new Error("Build can only be called once");
		build=true;
		Robot r = new Robot(speed, view, origin, degrees);
	
		LightSensor lightSensor = new LightSensor(r);
		SensorHolder holder = new SensorHolder(r);
		UltrasonicSensor ultraSonicSensor = new UltrasonicSensor(r, holder);
		holder.setUltraSonicSensor(ultraSonicSensor);
		IRSeekerV2 seeker = new IRSeekerV2(r,holder);
		TouchSensor touch = new TouchSensor(r);
		holder.setIRSensor(seeker);
		r.setTouch(touch);
		ticker.add(r.getPilot());
		ticker.add(lightSensor);
		ticker.add(holder);
		ticker.add(seeker);
		ticker.add(touch);
		ticker.add(ultraSonicSensor);
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


	public void setOrigin(Point point)
	{
		this.origin = new Pointf(point.x*SIMINFO.PANELWIDTH+SIMINFO.PANELWIDTH/2,point.y*SIMINFO.PANELHEIGHT+SIMINFO.PANELHEIGHT/2);
	}

	public void setDegrees(int degrees)
	{
		this.degrees = degrees;
	}
	public void setTicker(Ticker ticker)
	{
		this.ticker=ticker;
		
	}
	
}
