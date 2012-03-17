package util.lazy;

import java.awt.Point;

import util.board.Board;
import util.transformed.Transformation;
/**
 * Greedy implementation of a transformed board.
 *
 */
public class TransFormedBoard extends Board
{
	public TransFormedBoard(Transformation trans,Board board)
	{
		for(Point point:board.getFilledPoints())
			this.add(trans.execute(board.getPanelAt(point)),trans.execute(point));
	}
}
