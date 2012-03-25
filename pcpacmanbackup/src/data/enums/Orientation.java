package data.enums;

import java.awt.Point;
import java.util.Random;

public enum Orientation
{
	NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);

	private int xPlus;
	private int yPlus;

	private Orientation(int x, int y)
	{
		xPlus = x;
		yPlus = y;
	}

	public int getXPlus()
	{
		return xPlus;
	}

	public int getYPlus()
	{
		return yPlus;
	}

	public static Orientation fromOrdinal(int i)
	{

		for (Orientation o : Orientation.values())
			if (o.ordinal() == i)
				return o;
		return null;
	}

	public Orientation addTo(Direction dir)
	{
		switch (this)
		{

		case NORTH:
			switch (dir)
			{
			case LEFT:
				return WEST;
			case UP:
				return this;
			case RIGHT:
				return EAST;
			case DOWN:
				return SOUTH;

			}
			break;
		case EAST:
			switch (dir)
			{
			case LEFT:
				return NORTH;
			case UP:
				return this;
			case RIGHT:
				return SOUTH;
			case DOWN:
				return WEST;

			}
			break;

		case WEST:
			switch (dir)
			{
			case LEFT:
				return SOUTH;
			case UP:
				return this;
			case RIGHT:
				return NORTH;
			case DOWN:
				return EAST;

			}
			break;

		case SOUTH:
			switch (dir)
			{
			case LEFT:
				return EAST;
			case UP:
				return this;
			case RIGHT:
				return WEST;
			case DOWN:
				return NORTH;

			}
			break;

		default:
			break;
		}

		return null;
	}

	public Point addTo(Point p)
	{
		return new Point(p.x + this.xPlus, p.y + this.yPlus);
	}

	public Orientation opposite()
	{
		switch (this)
		{
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		case EAST:
			return WEST;
		}
		return null;
	}

	//private static int notRandom;
	//private static int[] s = {0,1,0,1};
	public static Orientation random()
	{
		return NORTH;
//		return fromOrdinal(new Random().nextInt(3));
		//return fromOrdinal(s[notRandom++]);
	}
}
