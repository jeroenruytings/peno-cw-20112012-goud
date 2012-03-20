package data.lazy;

import static data.board.operations.Operations.turn;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import data.board.Panel;
import data.board.operations.Operations.Turn;
import data.enums.Orientation;
import data.transformed.Transformation;
import data.world.RobotData;
import data.world.RobotDataView;

import pacmansystem.ai.robot.Barcode;
public class _TestTransformedRobotData
{
	private Panel panel1;
	@Before
	public void runBefore()
	{
		panel1	= new Panel();
		panel1.setBorder(Orientation.NORTH, true);
		panel1.setBorder(Orientation.EAST, true);
		panel1.setBarcode(new Barcode(211));
		panel1.setBarcodeOrientation(Orientation.NORTH);
	}
	@Test
	public void test0()
	{
		RobotData data = new RobotData();
		data.setPosition(new Point(10, 10));
		RobotData that = new RobotData();
		that.setPosition(new Point(5, 5));
		RobotDataView viewdata = 
				new TransformedRobotData(
						new Transformation(
								new Point(3, 3),
								new Point(4, 4),
								Orientation.NORTH,
								Orientation.NORTH)
						, data);
		assertTrue(viewdata.getPosition().equals(new Point(9,9)));
	}
	@Test
	public void test1()
	{
		RobotData data = new RobotData();
		data.setPosition(new Point(5, 10));
		RobotData that = new RobotData();
		that.setPosition(new Point(5, 5));
		RobotDataView viewdata = 
				new TransformedRobotData(
						new Transformation(
								new Point(3, 3),
								new Point(3, 3),
								Orientation.NORTH,
								Orientation.WEST)
						, data);
		assertTrue(viewdata.getPosition().equals(new Point(-10,5)));
	}
	@Test
	public void test2()
	{
		RobotData data = new RobotData();
		data.setPosition(new Point(10, 10));
		data.getBoard().add(panel1, new Point(3,3));
		RobotData that = new RobotData();
		that.setPosition(new Point(5, 5));
		that.getBoard().add(turn(panel1,Turn.LEFT), new Point(4,4));
		RobotDataView viewdata = 
				new TransformedRobotData(
						new Transformation(data,that)
						, data);
		System.out.println(viewdata.getPosition());
	}
}
