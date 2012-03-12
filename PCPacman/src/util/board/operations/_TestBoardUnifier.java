package util.board.operations;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashMap;

import org.junit.Test;

import pacmansystem.ai.robot.Barcode;

import util.board.Board;
import util.board.Panel;

public class _TestBoardUnifier
{
	@Test
	public void barcodeEqualTest()
	{
		assertTrue(new Barcode(1).equals(new Barcode(1)));
	}
	@Test
	public void barcodeEqualTestInSet()
	{
		HashMap<Barcode, String> h = new HashMap<Barcode,String>();
		h.put(new Barcode(1), "abra");
		assertTrue(h.keySet().contains(new Barcode(1)));
	}
	
	@Test
	public void simpleBoard()
	{
		Board thiz = genBoard1();
		Board that = genBoard2();
		Board result = BoardUnifier.unify(thiz, that);
		for(Point p:result.getFilledPoints())
			System.out.println(p);
		assertTrue(true);
	}

	private Board genBoard1()
	{
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		Panel p4 = new Panel();
		p1.setBarcode(new Barcode(155));
		p2.setBarcode(new Barcode(135));
		Board rv = new Board();
		rv.add(p1, new Point(1,1));
		rv.add(p2, new Point(1,2));
		rv.add(p3, new Point(1,3));
		rv.add(p4, new Point(1,4));
		return rv;
	}

	private Board genBoard2()
	{
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		Panel p4 = new Panel();
		p1.setBarcode(new Barcode(155));
		p2.setBarcode(new Barcode(135));
		Board rv = new Board();
		rv.add(p2, new Point(0,0));
		rv.add(p1, new Point(1,0));
		rv.add(p3, new Point(1,1));
		rv.add(p4, new Point(1,2));
		
		return rv;
	}

}