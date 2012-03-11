package util.board.operations;

import java.awt.Point;

import util.board.Board;

public  class Operations
{
	public static enum Turn
	{

		LEFT(new turn()
		{

			@Override
			public Point exec(Point point)
			{

				return new Point(-point.y, point.x);
			}

		}), RIGHT(new turn()
		{

			@Override
			public Point exec(Point point)
			{

				return new Point(point.y, -point.x);
			}

		});
		private turn turn;

		private Turn(turn turn)
		{
			this.turn = turn;
		}

		public  Point exec(Point point)
		{

			return this.turn.exec(point);
		}

		private interface turn
		{
			Point exec(Point point);
		}
	}
	/**
	 * Returns thiz point minus that point
	 * (1,1) - (2,2) = (-1,-1)
	 */
	public static Point min(Point thiz,Point that)
	{
		return new Point(thiz.x-that.x,thiz.y-that.y);
	}
	public static Point translate(Point point, Point vector)
	{
		return new Point(point.x + vector.x, point.y + vector.y);
	}

	public static Point negate(Point vector)
	{
		return new Point(-vector.x, -vector.y);
	}

	public static Board translate(Board board, Point vector)
	{
		Board rv = new Board();
		for (Point point : board.getFilledPoints()) {
			rv.add(board.getPanelAt(point), translate(point, vector));
		}
		return rv;
	}

	public static Point turn(Point point, Point around, Turn turn)
	{
		return translate(turn.exec(translate(point, negate(around))), around);
	}

	/**
	 * Turn around origin in the direction specified by turn
	 * 
	 * @return
	 */
	public static Point turn(Point point, Turn turn)
	{
		return turn.exec(point);
	}

	public static Board turn(Board board, Point around, Turn turn)
	{
		Board rv = new Board();
		for (Point point : board.getFilledPoints()) {
			rv.add(board.getPanelAt(point), turn(point, around, turn));
		}
		return rv;
	}

}
