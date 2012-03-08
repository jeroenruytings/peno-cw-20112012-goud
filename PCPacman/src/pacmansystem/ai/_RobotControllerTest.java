package pacmansystem.ai;

import static org.junit.Assert.*;
import java.awt.Point;

import org.junit.Test;

import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import util.board.Board;
import util.board.Panel;
import util.enums.Orientation;

public class _RobotControllerTest
{

	@Test
	public void nextMoveTest()
	{
		Board one = new Board(4, 4);

		RobotController main = new RobotController(one, new OrientationLayer(new SimulatedRobot(null)));

		Panel p1 = new Panel();
		p1.setBorder(Orientation.NORTH, true);
		one.add(p1, new Point(0, 0));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(1, 0));
		main.setCurrentX(1);
		main.setCurrentY(0);
		
		Panel p2 = new Panel();
		p2.setBorder(Orientation.SOUTH, true);
		p2.setBorder(Orientation.EAST, true);
		one.add(p2, new Point(1,0));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(1, 1));
		main.setCurrentX(1);
		main.setCurrentY(1);

		Panel p6 = new Panel();
		p6.setBorder(Orientation.NORTH, true);
		p6.setBorder(Orientation.EAST, true);
		one.add(p6, new Point(1, 1));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(0, 1));
		main.setCurrentX(0);
		main.setCurrentY(1);

		Panel p5 = new Panel();
		p5.setBorder(Orientation.SOUTH, true);
		one.add(p5, new Point(0, 1));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(0,2));
		main.setCurrentX(0);
		main.setCurrentY(2);
		
		Panel p9 = new Panel();
		p9.setBorder(Orientation.EAST, true);
		one.add(p9, new Point(0, 2));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(0, 3));
		main.setCurrentX(0);
		main.setCurrentY(3);
		
		Panel p13 = new Panel();
		one.add(p13, new Point(0, 3));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(1,3));
		main.setCurrentX(1);
		main.setCurrentY(3);

		Panel p14 = new Panel();
		one.add(p14, new Point(1,3));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(2, 3));
		main.setCurrentX(2);
		main.setCurrentY(3);

		Panel p15 = new Panel();
		p15.setBorder(Orientation.SOUTH, true);
		one.add(p15, new Point(2, 3));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(3, 3));
		main.setCurrentX(3);
		main.setCurrentY(3);

		Panel p16 = new Panel();
		one.add(p16, new Point(3, 3));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(3, 2));
		main.setCurrentX(3);
		main.setCurrentY(2);

		Panel p12 = new Panel();
		one.add(p12, new Point(3, 2));
		p12.setBorder(Orientation.WEST, true);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(3, 1));
		main.setCurrentX(3);
		main.setCurrentY(1);

		Panel p8 = new Panel();
		p8.setBorder(Orientation.WEST, true);
		one.add(p8, new Point(3, 1));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(3, 0));
		main.setCurrentX(3);
		main.setCurrentY(0);

		Panel p4 = new Panel();
		one.add(p4, new Point(3, 0));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(2,0));
		main.setCurrentX(2);
		main.setCurrentY(0);

		Panel p3 = new Panel();
		p3.setBorder(Orientation.NORTH, true);
		p3.setBorder(Orientation.WEST, true);
		one.add(p3, new Point(2, 0));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(1, 3));
		main.setCurrentX(1);
		main.setCurrentY(3);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(1, 2));
		main.setCurrentX(1);
		main.setCurrentY(2);

		Panel p10 = new Panel();
		p10.setBorder(Orientation.WEST, true);
		p10.setBorder(Orientation.SOUTH, true);
		one.add(p10, new Point(1,2));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(), new Point(2, 2));
		main.setCurrentX(2);
		main.setCurrentY(2);

		Panel p11 = new Panel();
		p11.setBorder(Orientation.EAST, true);
		p11.setBorder(Orientation.NORTH, true);
		one.add(p11, new Point(2, 2));
		System.out.println(main.lookForDestination());
		assertNull(main.lookForDestination());
	}
}
