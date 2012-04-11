package data.board.operations;

import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.HashMap;

import org.junit.Test;

import pacmansystem.ai.robot.Barcode;
import data.board.Board;
import data.board.Panel;
import data.enums.Orientation;
import data.transformed.Transformation;
import data.world.RobotData;

public class _TestBoardUnifier
{
	@Test
	public void calcTurnsTest0()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211), Orientation.NORTH);
		two.setBarcode(new Barcode(new Barcode(211).getReverse()), Orientation.EAST);
		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==3);
	}
	@Test
	public void calcTurnsTest5()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211), Orientation.NORTH);
		two.setBarcode(new Barcode(new Barcode(211).getReverse()), Orientation.WEST);

		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==1);
	}
	@Test
	public void calcTurnsTest1()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211), Orientation.NORTH);
		two.setBarcode(new Barcode(211), Orientation.EAST);
		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==1);
	}
	@Test
	public void calcTurnsTest2()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211), Orientation.EAST);
		two.setBarcode(new Barcode(211), Orientation.NORTH);
		int count =BoardUnifier.calculateTurns(one, two);
		assertTrue(count==3);
	}@Test
	public void calcTurnsTest3()
	{
		Panel one = new Panel();
		Panel two = new Panel();
		one.setBarcode(new Barcode(211), Orientation.NORTH);
		two.setBarcode(new Barcode(211), Orientation.NORTH);
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
	@Test
	public void testunify3()
	{
		Board thiz = genBoard1();
		Board that = genBoard2();
		assertTrue(BoardUnifier.unify2(thiz, that).equals(BoardUnifier.unify3(thiz, that)));
	}
	@Test
	public void test4()
	{

		Board thiz = genBoard1();
		Board that = genBoard2();
		RobotData data = new RobotData();
		RobotData data2 = new RobotData();
		data.setBoard(thiz);
		data2.setBoard(that);
		new Transformation(data,data2);
	}

	private Board genBoard1()
	{
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		Panel p4 = new Panel();
		p1.setBarcode(new Barcode(155), Orientation.NORTH);
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
		p1.setBarcode(new Barcode(155), Orientation.NORTH);
		Board rv = new Board();
		rv.add(p2, new Point(0,0));
		rv.add(p1, new Point(1,0));
		rv.add(p3, new Point(1,1));
		rv.add(p4, new Point(1,2));
		
		return rv;
	}

}
