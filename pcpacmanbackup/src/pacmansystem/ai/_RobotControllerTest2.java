//package pacmansystem.ai;
//
//import java.awt.Point;
//
//import org.junit.Test;
//
//import pacmansystem.ai.robot.OrientationLayer;
//import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
//import util.board.Panel;
//import util.enums.Orientation;
//import util.world.RealWorld;
//
//public class _RobotControllerTest2
//{
//	@Test
//	public void test()
//	{
//		RealWorld realworld = new RealWorld();
//		realworld.getGlobalBoard().add(new Panel(), new Point(0,0));
//		realworld.getGlobalBoard().add(new Panel(), new Point(0,1));
//		realworld.getGlobalBoard().add(new Panel(), new Point(1,0));
//		realworld.getGlobalBoard().add(new Panel(), new Point(1,1));
//		RobotController controller = new RobotController(new OrientationLayer(new SimulatedRobot(realworld, new Point(1,1),Orientation.NORTH)));
//		controller.explore();
//		System.out.println("abra");
//	}
//}
