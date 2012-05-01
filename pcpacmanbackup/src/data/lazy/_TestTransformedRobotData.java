package data.lazy;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.Barcode;
import data.board.Panel;
import data.board.Panel.WallState;
import data.board.operations.Operations;
import data.board.operations.Operations.Turn;
import data.enums.Orientation;
import data.transformed.Transformation;
import data.world.RobotData;
import data.world.RobotDataView;
public class _TestTransformedRobotData
{
	private Panel panel1;
	private Panel panel2;
	@Before
	public void runBefore()
	{
		panel1	= new Panel();
		panel1.setBorder(Orientation.NORTH, WallState.WALL);
		panel1.setBorder(Orientation.EAST, WallState.WALL);
		panel1.setBarcode(new Barcode(37), Orientation.NORTH);
		panel2	= new Panel();
		panel2.setBarcode(new Barcode(new Barcode(37).getReverse()), Orientation.NORTH);
	}

	@Test
	public void test2()
	{
		RobotData me = new RobotData();
		me.getBoard().add(panel1, new Point(0,0));
		RobotData that = new RobotData();
		that.setPosition(new Point(2,2 ));
		that.getBoard().add(panel1, new Point(1,1));
		RobotDataView viewdata = 
				new TransformedRobotData(
						new Transformation(me,that)
						, that);
		assertTrue(viewdata.getPosition().equals(new Point(1,1)));
	}
	@Test
	public void test3()
	{
		RobotData me = new RobotData();
		me.getBoard().add(panel1, new Point(0,0));
		RobotData that = new RobotData();
		that.setPosition(new Point(1,0 ));
		that.getBoard().add(Operations.turn(panel1, Turn.RIGHT), new Point(0,0));
		RobotDataView viewdata = 				new TransformedRobotData(	new Transformation(me,that), that);
		assertTrue(viewdata.getPosition().equals(new Point(0,1)));
	}@Test
	public void test7()
	{
		RobotData me = new RobotData();
		me.getBoard().add(panel1, new Point(0,0));
		RobotData that = new RobotData();
		that.getBoard().add(panel2, new Point(0,0));
		that.setPosition(new Point(1,-1 ));
		RobotDataView viewdata = 
				new TransformedRobotData(
						new Transformation(me,that)
						, that);
		assertTrue(viewdata.getPosition().equals(new Point(-1,1)));
	}
	@Test
	public void test6()
	{
		RobotData me = new RobotData();
		me.getBoard().add(panel1, new Point(0,0));
		RobotData that = new RobotData();
		that.getBoard().add(new Panel(1,0,0,0), new Point(1,-1));
		that.getBoard().add(panel2, new Point(0,0));
		RobotDataView viewdata = new TransformedRobotData(new Transformation(me,that)						, that);
		assertTrue(viewdata.getBoard().getPanelAt(new Point(-1,1)).hasBorder(Orientation.SOUTH));
	}
	@Test
	public void test5()
	{
		RobotData me = new RobotData();
		me.getBoard().add(panel1, new Point(0,0));
		RobotData that = new RobotData();
		that.setPosition(new Point(1,-1 ));
		that.getBoard().add(Operations.turn(panel1, Turn.RIGHT), new Point(0,0));
		
					Transformation viewdata=	new Transformation(me,that);
						assertTrue(viewdata.invert().execute(new Point(-1,-1)).equals(new Point(1,-1)));
	}
	@Test
	public void test4()
	{
		RobotData me = new RobotData();
		me.getBoard().add(panel1, new Point(0,0));
		RobotData that = new RobotData();
		that.setPosition(new Point(2,0 ));
		that.getBoard().add(Operations.turn(panel1, Turn.RIGHT), new Point(1,1));
		RobotDataView viewdata = 
				new TransformedRobotData(
						new Transformation(me,that)
						, that);
		assertTrue(viewdata.getPosition().equals(new Point(-1,-1)));
	}
	@Test
	public void testAllthethings()
	{
		Barcode barcode = new Barcode(37);
		//Barcode barcode2=new Barcode(barcode.getReverse());
		Panel one = new Panel();
		one.setBarcode(barcode, Orientation.NORTH);
		Panel two = new Panel();
		two.setBarcode(barcode, Orientation.WEST);
		RobotData data1 = new RobotData();
		data1.getBoard().add(one,new Point(0,0));
		RobotData data2 = new RobotData();
		data2.getBoard().add(two, new Point(0,0));
		data2.setPosition(new Point(1,0));
		Transformation trans = new Transformation(data1, data2);
		TransformedRobotData transformed = new TransformedRobotData(trans, data2);
		assertTrue(transformed.getPosition().equals(new Point(0,-1)));
	}
	@Test
	public void testAllthethings2()
	{
		Barcode barcode = new Barcode(37);
		Barcode barcode2=new Barcode(barcode.getReverse());
		Panel one = new Panel();
		one.setBarcode(barcode, Orientation.WEST);
		Panel two = new Panel();
		two.setBarcode(barcode2, Orientation.SOUTH);
		RobotData data1 = new RobotData();
		data1.getBoard().add(one,new Point(0,0));
		RobotData data2 = new RobotData();
		data2.getBoard().add(two, new Point(0,0));
		data2.setPosition(new Point(0,-1));
		Transformation trans = new Transformation(data1, data2);
		TransformedRobotData transformed = new TransformedRobotData(trans, data2);
		assertTrue(transformed.getPosition().equals(new Point(1,0)));
	}
	@Test
	public void testAllthethings3()
	{
		Barcode barcode = new Barcode(37);
		Barcode barcode2=new Barcode(barcode.getReverse());
		Panel one = new Panel();
		one.setBarcode(barcode, Orientation.WEST);
		Panel two = new Panel();
		two.setBarcode(barcode2, Orientation.SOUTH);
		RobotData data1 = new RobotData();
		data1.getBoard().add(one,new Point(0,0));
		RobotData data2 = new RobotData();
		data2.getBoard().add(two, new Point(0,0));
		data2.getBoard().add(new Panel(0,0,1,0),new Point(0,-1));
		Transformation trans = new Transformation(data1, data2);
		TransformedRobotData transformed = new TransformedRobotData(trans, data2);
		assertTrue(transformed.getBoard().getPanelAt(new Point(1,0)).equals(new Panel(0,1,0,0)));
	}
	@Test
	public void testAllthethings4()
	{
		Barcode barcode = new Barcode(37);
		Panel one = new Panel();
		one.setBarcode(barcode, Orientation.WEST);
		Panel two = new Panel();
		two.setBarcode(barcode, Orientation.SOUTH);
		RobotData data1 = new RobotData();
		data1.getBoard().add(one,new Point(0,0));
		RobotData data2 = new RobotData();
		data2.getBoard().add(two, new Point(0,0));
		data2.getBoard().add(new Panel(0,0,1,0),new Point(0,-1));
		Transformation trans = new Transformation(data1, data2);
		TransformedRobotData transformed = new TransformedRobotData(trans, data2);
		assertTrue(transformed.getBoard().getPanelAt(new Point(-1,0)).equals(new Panel(0,1,0,0)));
	}
	
	
}
