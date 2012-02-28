package mainController;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import board.Board;
import board.Panel;

public class _MainControllerTest {
	
	@Test
	public void nextMoveTest(){
		Board one = new Board(4,4);
		
		Panel p1 = new Panel();
//		Panel p3 = new Panel();
//		Panel p4 = new Panel();
//		Panel p5 = new Panel();
//		Panel p6 = new Panel();
//		Panel p7 = new Panel();
//		Panel p8 = new Panel();
//		Panel p9 = new Panel();
//		Panel p10 = new Panel();
//		Panel p11 = new Panel();
//		Panel p12 = new Panel();
//		Panel p13 = new Panel();
//		Panel p14 = new Panel();
//		Panel p15 = new Panel();
//		Panel p16 = new Panel();
		p1.setBorder(Orientation.EAST, true);
//		p3.setBorder(Orientation.SOUTH, true);
//		p5.setBorder(Orientation.SOUTH, true);
//		p6.setBorder(Orientation.SOUTH, true);
//		p6.setBorder(Orientation.EAST, true);
//		p7.setBorder(Orientation.NORTH, true);
//		p7.setBorder(Orientation.WEST, true);
//		p9.setBorder(Orientation.NORTH, true);
//		p9.setBorder(Orientation.EAST, true);
//		p10.setBorder(Orientation.NORTH, true);
//		p10.setBorder(Orientation.SOUTH, true);
//		p10.setBorder(Orientation.WEST, true);
//		p11.setBorder(Orientation.EAST, true);
//		p11.setBorder(Orientation.SOUTH, true);
//		p12.setBorder(Orientation.WEST, true);
//		p14.setBorder(Orientation.NORTH, true);
//		p15.setBorder(Orientation.NORTH, true);
		one.add(p1, new Point(0,0));
//		one.add(p3, new Point(2,0));
//		one.add(p4, new Point(3,0));
//		one.add(p5, new Point(0,1));
//		one.add(p6, new Point(1,1));
//		one.add(p7, new Point(2,1));
//		one.add(p8, new Point(3,1));
//		one.add(p9, new Point(0,2));
//		one.add(p10, new Point(1,2));
//		one.add(p11, new Point(2,2));
//		one.add(p12, new Point(3,2));
//		one.add(p13, new Point(0,3));
//		one.add(p14, new Point(1,3));
//		one.add(p15, new Point(2,3));
//		one.add(p16, new Point(3,3));
		
		
		
		MainController main = new MainController(one);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(0,1));
		
		Panel p5 = new Panel();
		p5.setBorder(Orientation.SOUTH, true);
		one.add(p5, new Point(0,1));
		main.setCurrentX(0);
		main.setCurrentY(1);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(1,1));
		
		Panel p6 = new Panel();
		p6.setBorder(Orientation.SOUTH, true);
		p6.setBorder(Orientation.EAST, true);
		one.add(p6, new Point(1,1));
		main.setCurrentX(1);
		main.setCurrentY(1);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(1,0));
		
		Panel p2 = new Panel();
		p2.setBorder(Orientation.WEST, true);
		one.add(p2, new Point(1,0));
		main.setCurrentX(1);
		main.setCurrentY(1);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(1,0));
		
		//(1,0)
		Panel p3 = new Panel();
		one.add(p3, new Point(2,0));
		main.setCurrentX(1);
		main.setCurrentY(0);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(2,0));
		
		Panel p4 = new Panel();
		p3.setBorder(Orientation.SOUTH, true);
		one.add(p4, new Point(3,0));
		main.setCurrentX(2);
		main.setCurrentY(0);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(3,0));
		
		Panel p8 = new Panel();
		one.add(p8, new Point(3,1));
		main.setCurrentX(3);
		main.setCurrentY(0);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(3,1));
		
		Panel p7 = new Panel();
		Panel p12 = new Panel();
		p7.setBorder(Orientation.NORTH, true);
		p7.setBorder(Orientation.WEST, true);
		one.add(p7, new Point(2,1));
		one.add(p12, new Point(3,2));
		main.setCurrentX(3);
		main.setCurrentY(1);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(3,2));
		
		Panel p16 = new Panel();
		p12.setBorder(Orientation.WEST, true);
		one.add(p16, new Point(3,3));
		main.setCurrentX(3);
		main.setCurrentY(2);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(3,3));
		
		Panel p15 = new Panel();
		one.add(p15, new Point(2,3));
		main.setCurrentX(3);
		main.setCurrentY(3);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(2,3));
		
		Panel p14 = new Panel();
		p15.setBorder(Orientation.NORTH, true);
		one.add(p14, new Point(1,3));
		main.setCurrentX(2);
		main.setCurrentY(3);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(1,3));
		
		Panel p13 = new Panel();
		p14.setBorder(Orientation.NORTH, true);
		one.add(p13, new Point(0,3));
		main.setCurrentX(1);
		main.setCurrentY(3);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(0,3));
		
		Panel p9 = new Panel();
		p9.setBorder(Orientation.NORTH, true);
		one.add(p9, new Point(0,2));
		main.setCurrentX(0);
		main.setCurrentY(3);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(0,2));
		
		p9.setBorder(Orientation.EAST, true);
		main.setCurrentX(0);
		main.setCurrentY(2);
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(2,1));
		main.setCurrentX(2);
		main.setCurrentY(1);
		
		Panel p11 = new Panel();
		p11.setBorder(Orientation.EAST, true);
		p11.setBorder(Orientation.SOUTH, true);
		one.add(p11, new Point(2,2));
		System.out.println(main.lookForDestination());
		assertEquals(main.lookForDestination(),new Point(2,2));
		main.setCurrentX(2);
		main.setCurrentY(2);
		
		Panel p10 = new Panel();
		p10.setBorder(Orientation.NORTH, true);
		p10.setBorder(Orientation.WEST, true);
		p10.setBorder(Orientation.SOUTH, true);
		one.add(p10, new Point(1,2));
		System.out.println(main.lookForDestination());
		assertNull(main.lookForDestination());
	}
}
