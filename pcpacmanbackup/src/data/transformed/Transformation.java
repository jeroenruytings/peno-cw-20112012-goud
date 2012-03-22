package data.transformed;

import static data.board.operations.BoardUnifier.filterCodes;
import static data.board.operations.BoardUnifier.findBarcodes;
import static data.board.operations.Operations.min;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;

import data.board.Panel;
import data.board.operations.BoardUnifier;
import data.board.operations.Operations;
import data.board.operations.Operations.Turn;
import data.enums.Direction;
import data.enums.Orientation;
import data.world.RobotData;
import data.world.RobotDataView;

import pacmansystem.ai.robot.Barcode;
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
	public Transformation(RobotData thiz, RobotDataView that)
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
	public static boolean canBeBuild(RobotData thiz,RobotData that)
	{
		return filterCodes(findBarcodes(thiz.getBoard()), findBarcodes(that.getBoard())).size()>0;
	}
	
	public Transformation invert()
	{
		return new InvertedTransformation(vector_, leftTurns_);
	}
	
	
	private class InvertedTransformation extends Transformation
	{

		public InvertedTransformation(Point vector, int leftTurns)
		{
			super(vector, leftTurns);
		}
		
		@Override
		public Point execute(Point point)
		{
			Point rv = point;
			for(int i = 0 ; i < leftTurns_;i++)
				rv = Operations.turn(rv, Turn.RIGHT);
			rv = Operations.translate(rv, Operations.negate(vector_));
			return rv;
			
		}
		@Override
		public Panel execute(Panel panel)
		{
			Panel rv =panel;
			for(int i = 0 ; i < leftTurns_;i++)
				rv = Operations.turn(rv, Turn.RIGHT);
			return rv;
		}
		public Transformation invert()
		{
			return new Transformation(vector_,leftTurns_);
		}
	}
	
}
