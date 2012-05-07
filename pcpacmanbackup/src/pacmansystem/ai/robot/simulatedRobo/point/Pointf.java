package pacmansystem.ai.robot.simulatedRobo.point;

import java.util.Arrays;


public final class Pointf implements Vector
{
	private float xy[] = new float[2];

	public Pointf(float x, float y)
	{
		xy[0] = x;
		xy[1] = y;
	}
	/**
	 * This constuctor should be used with care, it will cast the double to floats.
	 * @param x
	 * 	
	 * @param y
	 */
	public Pointf(double x, double y)
	{
		this((float)x,(float)y);
	}

	public float[] xy()
	{
		return Arrays.copyOf(xy, xy.length);
	}

	public float X()
	{
		return xy[0];
	}

	public float Y()
	{
		return xy[1];
	}
	@Override
	public String toString()
	{
		return "Pointf: ["+X()+","+Y()+"]";
	}
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Pointf) {
			Pointf that = (Pointf) o;
			return this.X() == that.X() && this.Y() == that.Y();
		} else {
			return false;
		}
	}
	

	@Override
	public Pointf add(Pointf point)
	{
		
		return new Pointf(this.X()+point.X(),this.Y()+point.Y());
	}
}
