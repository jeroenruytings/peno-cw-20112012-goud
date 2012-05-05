package pacmansystem.ai.robot.simulatedRobot.Robot;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobot.location.ComponentOriginComparator;
import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponentVisitor;
import pacmansystem.ai.robot.simulatedRobot.location.components.OpenComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.WallComponent;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.*;
public class UltrasonicSensor extends Sensor
{
	/**
	 * Sensor range in mm
	 */
	private static final long sensorRange=2000;
	private MovingComponent moving;
	private int distance;
	private boolean changed;

	public UltrasonicSensor(Robot robot,MovingComponent moving)
	{
		super(robot);
		this.moving =moving;
		}
	/**
	 * Returns the last read distance.
	 * @return
	 */
	public int getDistance()
	{

		return distance;
	}


	private Pointf getLocation()
	{
		Pointf here = moving.getLocation();
		return here;
	}

	@Override
	public void tick(Ticker ticker)
	{
		Pointf origin = getLocation();
		Pointf to = translate(origin, multiply(fromDegrees(moving.getDirection()), sensorRange));
		List<LocationComponent> components = robot.getView().getIn(origin, to);

		for(LocationComponent component:components)
		{
			PassabilityChecker p = new PassabilityChecker();
			component.visit(p);
			if(p.passable())
				continue;
			else
			{
				int newD =(int) new ComponentOriginComparator(origin, to).distance(component);
				if(newD==distance)
					break;
				distance=newD;
				this.changed =true;
				break;
				
			}
		}
	}
	private class PassabilityChecker implements LocationComponentVisitor
	{
		private boolean passable = true;

		public boolean passable()
		{
			return passable;
			
		}
		@Override
		public void visit(WallComponent wallc)
		{
			passable = false;
		}

		@Override
		public void visit(OpenComponent open)
		{
			passable=true;
		}
		
	}

	@Override
	public boolean hasChanged()
	{
		return changed;
	}
	public double getDirection()
	{
		return moving.getDirection();
	}
}
