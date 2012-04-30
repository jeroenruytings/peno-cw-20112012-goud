package pacmansystem.ai.robot.simulatedRobot.Robot;

import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.fromDegrees;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.multiply;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.translate;
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
	public LightSensor(Robot pilot)
	{
		super(pilot);
		robot.setLightSensor(this);
	}

	public int getLightValue()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int readRawValue()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasChanged()
	{
		return false;
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
		int degrees = robot.getDirection()-SIMINFO.LIGHTSENSORPOSITION;
		Pointf direction =new Pointf(Math.cos(degrees),Math.sin(degrees));
		return translate(robot.getLocation(),multiply(fromDegrees(degrees),SIMINFO.LIGHTSENSORDISTANCE));
	}


}
