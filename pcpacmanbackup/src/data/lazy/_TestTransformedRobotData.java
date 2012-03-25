package data.lazy;

import static data.board.operations.Operations.turn;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import data.board.Panel;
import data.board.Panel.WallState;
import data.board.operations.Operations;
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
		that.setPosition(new Point(2,0 ));
		that.getBoard().add(Operations.turn(panel1, Turn.RIGHT), new Point(0,0));
		RobotDataView viewdata = 
				new TransformedRobotData(
						new Transformation(me,that)
						, that);
		assertTrue(viewdata.getPosition().equals(new Point(0,2)));
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
		assertTrue(viewdata.getPosition().equals(new Point(-1,1)));
	}
}
