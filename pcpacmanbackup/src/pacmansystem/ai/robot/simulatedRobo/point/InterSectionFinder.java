package pacmansystem.ai.robot.simulatedRobo.point;

import static pacmansystem.ai.robot.simulatedRobo.point.Pointfs.*;

public class InterSectionFinder
{
	private Pointf one;
	private Pointf two;

	public InterSectionFinder(Pointf one, Pointf two)
	{
		this.one = one;
		this.two = two;
	}

	/**
	 * Returns the point where the line [one,two] and the line [p0,p1]
	 * intersect.
	 * 
	 * @return null if the lines have no intersection this is similar to the
	 *         hasIntersection method
	 * 
	 */
	public Pointf intersect(Pointf p0, Pointf p1)
	{
		if (!hasInterSection(p0, p1))
			return null;
		
		float Xt =(one.X()*two.Y()-one.Y()*two.X())*(p0.X()-p1.X())-(one.X()-two.X())*(p0.X()*p1.Y()-p0.Y()*p1.X());
		float denominator=(one.X()-two.X())*(p0.Y()-p1.Y())-(one.Y()-two.Y())*(p0.X()-p1.X());
		float Yt= (one.X()*two.Y()-one.Y()*two.X())*(p0.Y()-p1.Y())-(one.Y()-two.Y())*(p0.X()*p1.Y()-p0.Y()*p1.X());
	
		return new Pointf(Xt/denominator,Yt/denominator);
	}

	public boolean hasInterSection(Pointf p0, Pointf p1)
	{
		return Pointfs.crossing(one, two, p0, p1);
	}
}
