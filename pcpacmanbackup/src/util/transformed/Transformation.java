package util.transformed;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;

import pacmansystem.ai.robot.Barcode;

import util.board.Panel;
import util.board.operations.BoardUnifier;
import util.board.operations.Operations;
import util.board.operations.Operations.Turn;
import util.enums.Direction;
import util.enums.Orientation;
import util.world.RobotData;
import static util.board.operations.Operations.*;
import static util.board.operations.BoardUnifier.*;
public class Transformation
{
	private Point vector_;
	private int leftTurns_;
	public Transformation(Point vector,int leftTurns)
	{
		vector_=vector;
		leftTurns_=leftTurns;
	}
	public Transformation(Point one,Point two,Orientation o1,Orientation o2)
	{
		this(min(one, two),calculateTurns(o1, o2));
	}
	/**
	 * Returns a transformation of so that the axis of that are aligned with thiz
	 * @param thiz
	 * @param that
	 */
	public Transformation(RobotData thiz, RobotData that)
	{
		Map<Barcode,Point> barcodesThiz = findBarcodes(thiz.getBoard());
		Map<Barcode,Point> barcodesThat = findBarcodes(that.getBoard());
		Collection<Barcode> commonCodes =filterCodes(barcodesThiz, barcodesThat);
		Barcode barcode= commonCodes.iterator().next();
		Point origin = barcodesThiz.get(barcode);
		Point target=barcodesThat.get(barcode);
		int turns =BoardUnifier.calculateTurns(thiz.getBoard().getPanelAt(origin), that.getBoard().getPanelAt(target));
		this.leftTurns_=turns;
		this.vector_=min(origin,target);
	}
	private static int calculateTurns(Orientation orient1, Orientation orient2)
	{

		Direction direction = Direction.diff(orient1, orient2);
		switch(direction)
		{
		case UP:
			return 0;
		case DOWN:
			return 2;
		case LEFT:
			return 1;
		case RIGHT:
			return 3;
		}
		return 0;
	}
	public Point execute(Point point)
	{
		Point rv = Operations.translate(point, vector_);
		for(int i = 0 ; i < leftTurns_;i++)
			rv = Operations.turn(rv, Turn.LEFT);
		return rv;
	}
	public Panel execute(Panel panel)
	{
		Panel rv = panel;
		for(int i = 0 ; i < leftTurns_;i++)
			rv = Operations.turn(rv, Turn.LEFT);
		return rv;
	}
	public Orientation execute(Orientation orientation)
	{
		Orientation rv = orientation;
		for(int i = 0 ; i < leftTurns_;i++)
			rv =rv.addTo(Turn.LEFT.getDir());
		return rv;
	}
}
