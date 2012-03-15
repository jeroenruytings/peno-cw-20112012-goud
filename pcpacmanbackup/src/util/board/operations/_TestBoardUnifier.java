package util.board.operations;

import static org.junit.Assert.*;

import interfaces.mainscreen.Mainscreen;

import java.awt.Point;
import java.util.HashMap;

import org.junit.Test;

import pacmansystem.ai.robot.Barcode;

import util.board.Board;
import util.board.Panel;
import util.board.operations.Operations.Turn;
import util.enums.Orientation;
import util.world.RealWorld;

public class _TestBoardUnifier
{
	@Test
	public void calcTurnsTest0()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211));
		one.setBarcodeOrientation(Orientation.NORTH);
		two.setBarcode(new Barcode(new Barcode(211).getReverse()));
		two.setBarcodeOrientation(Orientation.EAST);
		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==3);
	}
	@Test
	public void calcTurnsTest5()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211));
		one.setBarcodeOrientation(Orientation.NORTH);
		two.setBarcode(new Barcode(new Barcode(211).getReverse()));
		two.setBarcodeOrientation(Orientation.WEST);
		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==1);
	}
	@Test
	public void calcTurnsTest1()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211));
		one.setBarcodeOrientation(Orientation.NORTH);
		two.setBarcode(new Barcode(211));
		two.setBarcodeOrientation(Orientation.EAST);
		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==1);
	}
	@Test
	public void calcTurnsTest2()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211));
		one.setBarcodeOrientation(Orientation.EAST);
		two.setBarcode(new Barcode(211));
		two.setBarcodeOrientation(Orientation.NORTH);
		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==3);
	}@Test
	public void calcTurnsTest3()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211));
		one.setBarcodeOrientation(Orientation.NORTH);
		two.setBarcode(new Barcode(211));
		two.setBarcodeOrientation(Orientation.NORTH);
		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==0);
	}
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
