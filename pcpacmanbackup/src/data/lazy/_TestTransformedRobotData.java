package data.lazy;

import static data.board.operations.Operations.turn;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import data.board.Panel;
import data.board.Panel.WallState;
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
		panel1.setBorder(Orientation.NORTH, WallState.WALL);
		panel1.setBorder(Orientation.EAST, WallState.WALL);
		panel1.setBarcode(new Barcode(37), Orientation.NORTH);
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
		RobotData me = new RobotData();
		me.getBoard().add(panel1, new Point(0,0));
		RobotData that = new RobotData();
		that.setPosition(new Point(2,2 ));
		that.getBoard().add(turn(panel1,Turn.RIGHT), new Point(1,1));
		RobotDataView viewdata = 
				new TransformedRobotData(
						new Transformation(me,that)
						, that);
		System.out.println(viewdata.getPosition());
	}
}
