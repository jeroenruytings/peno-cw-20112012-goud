package data.transformed;

import static data.board.operations.BoardUnifier.filterCodes;
import static data.board.operations.BoardUnifier.findBarcodes;
import static data.board.operations.Operations.min;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;

import data.board.Board;
import data.board.Panel;
import data.board.operations.BoardUnifier;
import data.board.operations.Operations;
import data.board.operations.Operations.Turn;
import data.enums.Direction;
import data.enums.Orientation;
import data.world.RobotData;
import data.world.RobotDataView;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.fysicalRobot.PanelColor;
public class Transformation
{
	private Point vector_;
	private int rightTurns;
	private Point pivot;
	private boolean codes;
	
	public boolean hasCodes(){
		return codes;
	}
	
	public Transformation(Point vector,Point pivot,int leftTurns)
	{
		vector_=vector;
		rightTurns=leftTurns;
		codes = true;
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
		if(commonCodes.size()>0)
			codes = true;
		else
			codes = false;
		Barcode barcode= commonCodes.iterator().next();
		Point origin = barcodesThiz.get(barcode);
		Point target=barcodesThat.get(barcode);
		
		this.vector_= min(origin,target);
		Board translated = Operations.translate(that.getBoard(), vector_);
		int turns =calculateTurns(thiz.getBoard().getPanelAt(origin), translated.getPanelAt(origin));
		this.rightTurns=turns;
		this.pivot=origin;
	}
	private static int calculateTurns(Panel panelThiz, Panel panelThat) {
		Barcode one = panelThiz.getBarcode();
		Barcode two = panelThat.getBarcode();
		Direction direction;
		Orientation orientation1 = panelThiz.getBarcodeOrientation();
		Orientation orientation2 = panelThat.getBarcodeOrientation();
		if(one.getValue()!=two.getValue())
			orientation2 = orientation2.opposite();
		int rightTurns = 0;
		Point target = orientation1.addTo(new Point(0,0));
		Point current = orientation2.addTo(new Point(0,0));
		while(!current.equals(target))
		{
			current = Operations.turn(current, Turn.RIGHT);
			rightTurns++;
		}
		return rightTurns;
		
	}
	
	public Point execute(Point point)
	{
		Point rv = Operations.translate(point, vector_);
		for(int i = 0 ; i < rightTurns;i++)
			rv = Operations.turn(rv,pivot, Turn.RIGHT);
		return rv;
	}
	public Panel execute(Panel panel)
	{
		Panel rv = panel;
		for(int i = 0 ; i < rightTurns;i++)
			rv = Operations.turn(rv, Turn.RIGHT);
		return rv;
	}
	public Orientation execute(Orientation orientation)
	{
		Orientation rv = orientation;
		for(int i = 0 ; i < rightTurns;i++)
			rv =rv.addTo(Turn.RIGHT.getDir());
		return rv;
	}
	public static boolean canBeBuild(RobotData thiz,RobotData that)
	{
		return filterCodes(findBarcodes(thiz.getBoard()), findBarcodes(that.getBoard())).size()>0;
	}
	
	public Transformation invert()
	{
		return new InvertedTransformation(vector_,pivot, rightTurns);
	}
	
	
	private class InvertedTransformation extends Transformation
	{

		public InvertedTransformation(Point vector,Point pivot, int leftTurns)
		{
			super(vector,pivot, leftTurns);
		}
		
		@Override
		public Point execute(Point point)
		{
			Point rv = point;
			for(int i = 0 ; i < rightTurns;i++)
				rv = Operations.turn(rv,pivot, Turn.LEFT);
			rv = Operations.translate(rv, Operations.negate(vector_));
			return rv;
			
		}
		@Override
		public Panel execute(Panel panel)
		{
			Panel rv =panel;
			for(int i = 0 ; i < rightTurns;i++)
				rv = Operations.turn(rv, Turn.LEFT);
			return rv;
		}
		@Override
		public Orientation execute(Orientation orientation)
		{
			Orientation rv = orientation;
			for(int i = 0 ; i < rightTurns;i++)
				rv =rv.addTo(Turn.LEFT.getDir());
			return rv;
		}
		@Override
		public Transformation invert()
		{
			return new Transformation(vector_,pivot,rightTurns);
		}
	}
	
}
