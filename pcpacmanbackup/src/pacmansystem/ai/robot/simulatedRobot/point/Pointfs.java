package pacmansystem.ai.robot.simulatedRobot.point;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public final class Pointfs
{

	public static Pointf invert(Pointf point)
	{
		return multiply(point, -1);
	}

	/**
	 * Returns the translated version of point following the point vector.
	 * 
	 * @param vector
	 * @param point
	 * @return
	 */
	public static Pointf translate(Pointf vector, Pointf point)
	{
		return new Pointf(point.X() + vector.X(), point.Y() + vector.Y());
	}

	/**
	 * Returns the dot product of the vector one and two
	 */
	public static float dotProduct(Pointf one, Pointf two)
	{
		return one.X() * two.Y() - one.Y() * two.X();
	}

	public static Pointf multiply(Pointf point, float val)
	{
		return new Pointf(point.X() * val, point.Y() * val);
	}

	/**
	 * Returns if the line [one,two] crosses [ three, four]
	 * 
	 * @return
	 */
	public static boolean crossing(Pointf one, Pointf two, Pointf three,
			Pointf four)
	{
		boolean v0 = max(one.X(), two.X()) >= min(three.X(), four.X());
		boolean v1 = min(one.X(), two.X()) <= max(three.X(), four.X());
		boolean v2 = max(one.Y(), two.Y()) >= min(three.Y(), four.Y());
		boolean v = v0 && v1 && v2;
		if (!v)
			return false;
		Pointf p1p2 = translate(two, invert(one));
		Pointf p1p3 = translate(three, invert(one));
		Pointf p1p4 = translate(four, invert(one));
		Pointf p3p4 = translate(four, invert(three));
		Pointf p3p1 = translate(one, invert(three));
		Pointf p3p2 = translate(two, invert(three));
		return dotProduct(p1p2, p1p3) * dotProduct(p1p2, p1p4) <= 0
				&& dotProduct(p3p4, p3p1) * dotProduct(p3p4, p3p2) <= 0;
	}

	public static float max(float one, float two)
	{
		if (one > two)
			return one;
		return two;
	}

	public static float min(float one, float two)
	{
		if (one < two)
			return one;
		return two;
	}

	/**
	 * Returns if the line [one,two] crosses the line formed by three as origin
	 * & a linear combination of four
	 * 
	 * @return
	 */
	public static boolean crossingDirection(Pointf one, Pointf two,
			Pointf three, Pointf four)
	{
		return crossing(one, two, three, multiply(four, 1000));
	}

	/**
	 * Returns if point is in the convex figure formed by points.
	 * 
	 * @param point
	 *            The point to be checked.
	 * @param points
	 *            The convex figure that might contain the point
	 * @return
	 */
	public static boolean in(Pointf point, Pointf... points)
	{
		if (point == null)
			throw new IllegalArgumentException("Dont pass in null as a point");
		if (points.length < 2)
			throw new IllegalArgumentException(
					"There must be at least 2 points passed");
		if (points.length == 2)
			return crossing(point, point, points[0], points[1]);
		// if point is on the same side of all pairs in points then all is well
		// :)
		int sign = 0;
		for(Segment segment: new SegmentIterator(points).getIterable())
		{
			float prod = dotProduct(translate(point, invert(segment.one)),
					translate(segment.two, invert(segment.one)));
			if (sign!=0&&sign(prod) != 0 && sign(prod) != sign)
				return false;
			sign = sign(prod);
		}
		return true;
	}

	private static final Pointf[] arr = new Pointf[1];

	public static boolean in(Pointf point, List<Pointf> points)
	{
		return in(point, points.toArray(arr));
	}

	private static int sign(float prod)
	{
		if (prod > 0)
			return 1;
		if (prod < 0)
			return -1;
		return 0;
	}

	public static boolean convexPoints(List<Pointf> points)
	{
		// TODO:Implement
		return true;
	}

	/**
	 * Gives a vector with length one in the direction of degrees where 0 is
	 * (1,0) and 90 is (0,1)
	 * 
	 * @param degrees
	 * @return
	 */
	public static final Pointf fromDegrees(double degrees)
	{
		return new Pointf(Math.cos(toRadians(degrees)),
				Math.sin(toRadians(degrees)));
	}

	public static final Pointf rotate(Pointf point, Pointf around, double degree)
	{
		double radian = toRadians(degree);
		Pointf rv = point;
		rv = translate(rv, invert(around));
		rv = new Pointf(cos(radian) * rv.X() - sin(radian) * rv.Y(),
				sin(radian) * rv.X() + sin(radian) * rv.Y());
		rv = translate(rv, around);
		return rv;
	}

	public static final List<Pointf> rotate(List<Pointf> points, Pointf around,
			double degrees)
	{
		ArrayList<Pointf> rv = new ArrayList<Pointf>();
		for (Pointf point : points)
			rv.add(rotate(point, around, degrees));
		return rv;
	}
	public static final float distance(Pointf one,Pointf two)
	{
		return (float) Math.sqrt(scalarProd(one,two));
	}

	public static double scalarProd(Pointf one, Pointf two)
	{
		return one.X()*two.X()+one.Y()*two.Y();
	}
}
