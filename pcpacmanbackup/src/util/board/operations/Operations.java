package util.board.operations;

import java.awt.Point;

import pacmansystem.ai.robot.Barcode;

import util.board.Board;
import util.board.Panel;
import util.enums.Direction;
import util.enums.Orientation;

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

		public Direction getDir() {
			switch(this)
			{
			case LEFT:
				return Direction.LEFT;
			case RIGHT:
				return Direction.RIGHT;
			}
			return Direction.UP;
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
			Panel p = board.getPanelAt(point);
			rv.add(turn(board.getPanelAt(point),turn), turn(point, around, turn));
		}
		return rv;
	}
	public static Panel turn(Panel panelAt, Turn turn) {
		Panel rv = new Panel();
		for(Orientation d:Orientation.values())
			rv.setBorder( d.addTo(turn.getDir()),panelAt.hasBorder(d));
		if(!panelAt.hasBarcode())
			return rv;
		rv.setBarcode(panelAt.getBarcode());
		rv.setBarcodeOrientation(panelAt.getBarcodeOrientation().addTo(turn.getDir()));
	
	return rv;
	}

}
