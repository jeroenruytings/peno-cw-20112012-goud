package data.lazy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import data.board.Board;
import data.board.Panel;
import data.enums.Orientation;
import data.transformed.Transformation;


public class TransformedBoardLazy extends Board
{
	private Board board_;
	private Transformation trans_;

	public TransformedBoardLazy(Transformation trans,Board board)
	{
		board_=board;
		trans_=trans;
	}

	@Override
	public int getRows()
	{
		return board_.getRows();
	}

	@Override
	public int getColumns()
	{
		return board_.getColumns();
	}

	@Override
	public boolean hasPanelAt(Point p)
	{
		
		return board_.hasPanelAt(trans_.invert().execute(p));
	}

	@Override
	public Panel getPanelAt(Point p)
	{
		
		return trans_.invert().execute(board_.getPanelAt(trans_.invert().execute(p)));
	}


	@Override
	public Iterable<Point> getFilledPoints()
	{
		ArrayList<Point> rv = new ArrayList<Point>();
		for(Point p:board_.getFilledPoints())
			rv.add(trans_.execute(p));
		return rv;
	}

	@Override
	public Collection<Point> getSurrounding(Point p)
	{
		
		return board_.getSurrounding(p);
	}

	@Override
	public boolean wallBetween(Point one, Orientation orientation)
	{
		
		return board_.wallBetween(trans_.invert().execute(one), orientation);
	}

	@Override
	public boolean wallBetween(Point one, Point two)
	{
		
		return board_.wallBetween(trans_.invert().execute(one), trans_.invert().execute(two));
	}

	@Override
	public Iterable<Point> getUnfilledPoints()
	{
		ArrayList<Point >rv = new ArrayList<Point>();
		for(Point point:board_.getUnfilledPoints())
			rv.add(trans_.execute(point));
		return rv;
	}
}
