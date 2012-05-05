package pacmansystem.ai.robot.simulatedRobot.location;

import java.util.Comparator;

import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobot.point.InterSectionFinder;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.point.Pointfs;
import pacmansystem.ai.robot.simulatedRobot.point.Segment;
import pacmansystem.ai.robot.simulatedRobot.point.SegmentIterator;

/**
 * Comparator to see how far a locatinocomponent is from an origin point, in a
 * direction
 * 
 */
public class ComponentOriginComparator implements Comparator<LocationComponent>
{

	private Pointf thiz;
	private Pointf that;
	private InterSectionFinder finder;

	/**
	 * The location comparator in the interval [thiz,that] where thiz is the
	 * origin point, this means that
	 */
	public ComponentOriginComparator(Pointf thiz, Pointf that)
	{
		this.thiz = thiz;
		this.that = that;
		finder = new InterSectionFinder(thiz, that);
	}

	@Override
	public int compare(LocationComponent arg0, LocationComponent arg1)
	{
		float distance0 = distance(arg0);
		float distance1 = distance(arg1);
		if (distance0 > distance1)
			return 1;
		if (distance0 < distance1)
			return -1;

		return 0;
	}

	/**
	 * Gives the distance that is minimal
	 * 
	 * @return Float.MAX_VALUE if there is no such value
	 */
	 float distance(LocationComponent arg0)
	{
		float min = Float.MAX_VALUE;
		for (Segment segment : new SegmentIterator(arg0.getConvexPoints())
				.getIterable()) {
			min = Math.min(min, distance(segment));
		}
		return min;
	}

	 float distance(Segment segment)
	{
		if (!finder.hasInterSection(segment.one, segment.two))
			return Float.MAX_VALUE;
		Pointf intersection = finder.intersect(segment.one, segment.two);
		return Pointfs.distance(thiz, intersection);
	}

}