package data.board.operations;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import data.board.Board;
import data.board.Panel;
import data.board.operations.Operations.Turn;
import data.enums.Direction;
import data.help.Filter;
import data.lazy.TransformedRobotData;
import data.transformed.Transformation;
import data.world.RobotData;

import pacmansystem.ai.robot.Barcode;

public class BoardUnifier
{
	public static Board unify(Board thiz,Board that)
	{
		Board rv = new Board();
		Map<Barcode,Point> barcodesThiz = findBarcodes(thiz);
		Map<Barcode,Point> barcodesThat = findBarcodes(that);
		ArrayList<Barcode> commonCodes = filterCodes(barcodesThiz, barcodesThat);
		if(commonCodes.size()<4)
			return thiz;
		Barcode barcode= commonCodes.get(0);
		Point origin = barcodesThiz.get(barcode);
		Point vector =Operations.min(origin, barcodesThat.get(barcode));
		Board translated = Operations.translate(that, vector);
		barcodesThat = findBarcodes(translated);
		Barcode other =commonCodes.get(2);
		for(Barcode c:commonCodes)
			if(!c.equals(barcode))
				other = c;
		Point p2 = barcodesThat.get(other);
		Point target = barcodesThiz.get(other);
		int count =0;
		while(count<4&&!p2.equals(target))
		{
			count++;
			p2 = Operations.turn(p2,origin, Turn.LEFT);
		}
		if(count==4)
			return thiz;//something went wrong
		
		for(int i =0;i<count;i++)
			translated=Operations.turn(translated, origin, Turn.LEFT);
		rv=merge(thiz,translated);
		return rv;
		
	}
	public static Board unify2(Board thiz,Board that)
	{
		Board rv = new Board();
		Map<Barcode,Point> barcodesThiz = findBarcodes(thiz);
		Map<Barcode,Point> barcodesThat = findBarcodes(that);
		ArrayList<Barcode> commonCodes = filterCodes(barcodesThiz, barcodesThat);
		if(commonCodes.size()<2)
			return thiz;
		Barcode barcode= commonCodes.get(0);
		Point origin = barcodesThiz.get(barcode);
		Point target=barcodesThat.get(barcode);
		Point vector =Operations.min(origin, target);
		Board translated = Operations.translate(that, vector);
		Panel panelThiz=thiz.getPanelAt(origin);
		Panel panelThat=translated.getPanelAt(origin);
		int count =calculateTurns(panelThiz,panelThat);
		for(int i =0;i<count;i++)
			translated=Operations.turn(translated, origin, Turn.LEFT);
		rv=merge(thiz,translated);
		return rv;
		
	}
	
	public static Board unify3(Board thiz, Board that)
	{
		thiz = new Board(thiz);
		RobotData data = new RobotData();
		RobotData data2 = new RobotData();
		data.setBoard(thiz);
		data2.setBoard(that);
		if(!Transformation.canBeBuild(data, data2))
			return new Board(thiz);
		TransformedRobotData OtherData = new TransformedRobotData(new Transformation(data,data2), data2);
		
		for(Point p:OtherData.getBoard().getFilledPoints())
			if(!thiz.hasPanelAt(p))
				{
				thiz.add(OtherData.getBoard().getPanelAt(p), p);
				System.out.println("we are unifying !");
				}
		
		return thiz;
		
	}
	public static int calculateTurns(Panel panelThiz, Panel panelThat) {
		Barcode one = panelThiz.getBarcode();
		Barcode two = panelThat.getBarcode();
		Direction direction;
		if(one.getValue()==two.getValue())
		{
		direction= Direction.diff(panelThat.getBarcodeOrientation(),panelThiz.getBarcodeOrientation());
		}else{
		direction=Direction.diff( panelThat.getBarcodeOrientation().opposite(),panelThiz.getBarcodeOrientation());	
		}
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
	
	/**
	 * Geeft een nieuw bord toe waarbij alle bekende punten behouden blijven en daarna nieuwe info van het andere bord toegevoegd wordt
	 * @param thiz
	 * @param translated
	 * @return
	 */
	private static Board merge(Board thiz, Board translated)
	{
		Board rv = new Board();
		for(Point p:thiz.getFilledPoints())
			rv.add(thiz.getPanelAt(p), p);
		for(Point p:translated.getFilledPoints())
		{
			if(!rv.hasPanelAt(p))
				rv.add(translated.getPanelAt(p), p);
		}
		return rv;
	}

	public static ArrayList<Barcode> filterCodes(
			Map<Barcode, Point> barcodesThiz, Map<Barcode, Point> barcodesThat)
	{
		return new ArrayList<Barcode>( Filter.filter(barcodesThiz.keySet(), new Filter<Barcode>(barcodesThat.keySet())
		{
			@SuppressWarnings("unchecked")
			@Override
			public boolean accepts(Barcode arg)
			{
				
				return new ArrayList<Barcode>((Set<Barcode>) this._objs[0]).contains(arg);
			}
		}));
	}

	public static Map<Barcode, Point> findBarcodes(Board board)
	{
		Map<Barcode,Point> rv = new HashMap<Barcode, Point>();
		for(Point p:board.getFilledPoints())
			if(board.getPanelAt(p).hasBarcode()){
				rv.put(board.getPanelAt(p).getBarcode(), p);

				rv.put(new Barcode(board.getPanelAt(p).getBarcode().getReverse()), p);}
		return rv;
	}
}
