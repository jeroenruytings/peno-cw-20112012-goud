package pacmansystem.ai.robot.simulatedRobot.location;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;

public interface RealWorldView
{

	/**
	 * Returns the components that contain this point, the RealWorldView allows
	 * for duplicate entries& will return all the components if a point is
	 * requested that is on the border of 2 Components.
	 * 
	 * @param point
	 *            the point in the 2d plane.
	 * @return the collection of components that are on this point.
	 */
	public abstract LocationComponent get(Pointf point);

	/**
	 * Gets all the components that are in the direction of pointf vector
	 */
	public abstract List<LocationComponent> get(Pointf point, Pointf vector);

	/**
	 * Gets the LocationComponents that are in the range [point0->point1]
	 */
	public abstract List<LocationComponent> getIn(Pointf point0, Pointf point1);

}