package data.enums;

import java.awt.Point;

public enum Direction
{

	UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);
	
	private Direction(int x, int y)
	{
	}



	public Direction opposite()
	{
		switch (this)
		{
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		default:
			throw new RuntimeException();
		}
	}
	public static Direction diff(Orientation one,Orientation two)
	{
		for(Direction d:values())
		{
			if(one.addTo(d).equals(two))
				return d;
		}
		return UP;
	}
};