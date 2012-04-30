package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponentVisitor;
import pacmansystem.ai.robot.simulatedRobot.location.components.OpenComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.WallComponent;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.*;
public class LightSensor  implements Sensor
{
	private Robot robot;
	private int value;
	private boolean changed =false;
	public LightSensor(Robot pilot)
	{
		this.robot=pilot;
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
		// now here magic has to happen
	}
	private Pointf getLocation()
	{
		return translate(robot.getCenterPoint(),multiply(robot.getDirection(),3));
	}


}
