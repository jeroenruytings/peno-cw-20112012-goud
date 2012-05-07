package pacmansystem.ai.robot.simulatedRobo.location;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobo.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobo.point.Pointf;

public interface RealWorldView
{

	/**
	 * Returns the components that contain this point, the RealWorldView allows
	 * for duplicate entries& will return all the components if a point is
	 * requested that is on the border of 2 Components.
	 * 
	 * @param point
	 *            the point in the 2d plane.
	 * @return the component with the highest z-value
	 */
	public abstract LocationComponent get(Pointf point);

	/**
	 * Gets the LocationComponents that are in the range [point0->point1]
	 * sorted, closest one first
	 */
	public abstract List<LocationComponent> getIn(Pointf point0, Pointf point1);
	/**
	 * Checks if this convex figure conflicts with the current realworldview.
	 * @param points
	 * @return
	 */
	public boolean conflicting (List<Pointf> points);
}