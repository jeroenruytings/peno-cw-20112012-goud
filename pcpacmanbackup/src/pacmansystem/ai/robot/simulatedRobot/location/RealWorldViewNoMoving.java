package pacmansystem.ai.robot.simulatedRobot.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.point.Pointfs;

public class RealWorldViewNoMoving implements RealWorldView
{
	private Collection<LocationComponent> comps_;

	RealWorldViewNoMoving()
	{
		comps_ = new ArrayList<LocationComponent>();
	}

	void add(LocationComponent component)
	{
		comps_.add(component);  	
	}

	/**
	 * Gets the Component with the highest z-value on this point
	 */
	@Override
	public LocationComponent get(Pointf point)
	{
		ArrayList<LocationComponent> rv = new ArrayList<LocationComponent>();
		for (LocationComponent comp : comps_)
			if (Pointfs.in(point, comp.getConvexPoints()))
				rv.add(comp);
		Collections.sort(rv, new Comparator<LocationComponent>()
		{

			@Override
			public int compare(LocationComponent o1, LocationComponent o2)
			{
				Comparable<Integer> thiz = o1.getZIndex();
				Integer that = o2.getZIndex();
				return thiz.compareTo(that);
			}

		});
		return rv.get(0);
	}

	/* (non-Javadoc)
	 * @see pacmansystem.ai.robot.simulatedRobot.location.RealWorldView#get(pacmansystem.ai.robot.simulatedRobot.location.Pointf, pacmansystem.ai.robot.simulatedRobot.location.Pointf)
	 */
	@Override
	public List<LocationComponent> get(Pointf point, Pointf vector)
	{

		ArrayList<LocationComponent> rv = new ArrayList<LocationComponent>();

		return rv;
	}

	/* (non-Javadoc)
	 * @see pacmansystem.ai.robot.simulatedRobot.location.RealWorldView#getIn(pacmansystem.ai.robot.simulatedRobot.location.Pointf, pacmansystem.ai.robot.simulatedRobot.location.Pointf)
	 */
	@Override
	public List<LocationComponent> getIn(Pointf point0, Pointf point1)
	{

		ArrayList<LocationComponent> rv = new ArrayList<LocationComponent>();

		return rv;
	}

	/**
	 * returns true if the passed point is in the convex collection of points
	 * that are given in list( will return true if the point is contained the
	 * list of points.
	 * 
	 * @param points
	 * @param point
	 * @return
	 */
	private enum SIDE
	{
		left, right, unknown;
		/**
		 * Returns the side of pointf p3 relative to points p1 & p2
		 * 
		 * @return the side p3 is on relative to p1,p2
		 */
		static SIDE side(Pointf p1, Pointf p2, Pointf p3)
		{

			return right;
		}
	}

	
	public static final boolean in(List<Pointf> points, Pointf origin,Pointf direction)
	{

		return false;

	}

	public SimpleView getSimpleView()
	{
		return new SimpleView(this);
	}
	public RealWorldView withMovingObjects()
	{
		return null;
	}

	@Override
	public boolean conflicting(List<Pointf> points)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
