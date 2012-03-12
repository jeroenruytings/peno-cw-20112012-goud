package util.board.operations;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import pacmansystem.ai.robot.Barcode;

import util.board.Board;
import util.board.operations.Operations.Turn;
import util.help.Filter;

public class BoardUnifier
{
	public static Board unify(Board thiz,Board that)
	{
		Board rv = new Board();
		Map<Barcode,Point> barcodesThiz = findBarcodes(thiz);
		Map<Barcode,Point> barcodesThat = findBarcodes(that);
		ArrayList<Barcode> commonCodes = filterCodes(barcodesThiz, barcodesThat);
		if(commonCodes.size()<2)
			return thiz;
		Barcode barcode= commonCodes.get(0);
		Point origin = barcodesThiz.get(barcode);
		Point vector =Operations.min(origin, barcodesThat.get(barcode));
		Board translated = Operations.translate(that, vector);
		barcodesThat = findBarcodes(translated);
		Point p2 = barcodesThat.get(commonCodes.get(1));
		Point target = barcodesThiz.get(commonCodes.get(1));
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

	private static ArrayList<Barcode> filterCodes(
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

	private static Map<Barcode, Point> findBarcodes(Board board)
	{
		Map<Barcode,Point> rv = new HashMap<Barcode, Point>();
		for(Point p:board.getFilledPoints())
			if(board.getPanelAt(p).hasBarcode())
				rv.put(board.getPanelAt(p).getBarcode(), p);
		return rv;
	}
}
