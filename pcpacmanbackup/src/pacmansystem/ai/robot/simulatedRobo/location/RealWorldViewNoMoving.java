package pacmansystem.ai.robot.simulatedRobo.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pacmansystem.ai.robot.simulatedRobo.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobo.point.Pointf;
import pacmansystem.ai.robot.simulatedRobo.point.Pointfs;

public class RealWorldViewNoMoving implements RealWorldView
{
	private Collection<LocationComponent> comps_;
	
	public RealWorldViewNoMoving()
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
				if(o1.getZIndex()>o2.getZIndex())
					return -1;
				if(o1.getZIndex()<o2.getZIndex())
					return 1;
				return 0;
				}

		});
		if(rv.isEmpty())
			return null;
		return rv.get(0);
	}



	/* (non-Javadoc)
	 * @see pacmansystem.ai.robot.simulatedRobot.location.RealWorldView#getIn(pacmansystem.ai.robot.simulatedRobot.location.Pointf, pacmansystem.ai.robot.simulatedRobot.location.Pointf)
	 */
	@Override
	public List<LocationComponent> getIn(Pointf point0, Pointf point1)
	{

		ArrayList<LocationComponent> rv = new ArrayList<LocationComponent>();
		for(LocationComponent comp : comps_)
		{
			//if point0 or point1 is in the component return true
			if(Pointfs.in(point0, comp.getConvexPoints())||Pointfs.in(point1, comp.getConvexPoints()))
				{
					rv.add(comp);
					continue;
				}
			//if the line [point0,point1] crosses one of the lines of the component
			if(crossesOne(comp.getConvexPoints(),point0,point1))
			{
				rv.add(comp);
			}
		}
		Collections.sort(rv, new ComponentOriginComparator(point0,point1));
		return rv;
	}

	private boolean crossesOne(List<Pointf> convexPoints, Pointf point0,
			Pointf point1)
	{
		Pointf last = null;
		for(Pointf point:convexPoints)
		{
			if(last==null)
			{
				last = point;
				continue;
			}
			if(Pointfs.crossing(point0, point1, last, point))
				return true;
			
		}
		return false;
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
