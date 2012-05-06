package pacmansystem.ai.robot.simulatedRobot.Robot;

import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.fromDegrees;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.multiply;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.translate;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import pacmansystem.ai.robot.simulatedRobot.SIMINFO;
import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponentVisitor;
import pacmansystem.ai.robot.simulatedRobot.location.components.OpenComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.WallComponent;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
public class LightSensor  extends Sensor
{
	private int value;
	private boolean changed =false;
	private boolean first=true;
	public LightSensor(Robot pilot)
	{
		super(pilot);
		robot.setLightSensor(this);
	}
	/**
	 * Consumes the changed value.
	 * Gives a value between 0 and 
	 * @return
	 */
	public int getLightValue()
	{
		changed=false;
		return value/4;
	}



	public boolean hasChanged()
	{
		return true;
	}

	@Override
	public void tick(Ticker ticker)
	{
		readValue();
	}
	private class ColorChecker implements LocationComponentVisitor{
		boolean colored = false;
		int color ;
		@Override
		public void visit(OpenComponent open)
		{
			color = open.getColor();
			colored=true;
		}
		
		@Override
		public void visit(WallComponent wallc)
		{
				
		}
	};
	private void readValue()
	{
		LocationComponent component= robot.getView().get(getLocation());
		if(component==null)
			return;
		ColorChecker c = new ColorChecker();
		component.visit(c);
		if(!c.colored)
			return;
		int color = c.color;
		if(value==color)
			return;
		value=color;
		changed=true;
	}
	private Pointf getLocation()
	{
		double degrees = robot.getDirection()-SIMINFO.LIGHTSENSORPOSITION;
		Pointf direction =new Pointf(Math.cos(degrees),Math.sin(degrees));
		;
		return robot.getLocation();//translate(robot.getLocation(),multiply(fromDegrees(degrees),SIMINFO.LIGHTSENSORDISTANCE));
	}


}
