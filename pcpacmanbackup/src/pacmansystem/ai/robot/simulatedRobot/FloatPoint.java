package pacmansystem.ai.robot.simulatedRobot;

public class FloatPoint implements Vector
{
	private float x;
	private float y;

	public FloatPoint(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float X()
	{
		return x;
	}

	public float Y()
	{
		return y;
	}

	@Override
	public FloatPoint add(FloatPoint point)
	{
		return new FloatPoint(x + point.x, y + point.y);
	}
}
