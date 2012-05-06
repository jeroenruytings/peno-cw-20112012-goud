
package pacmansystem;

import interfaces.mainscreen.Mainscreen;
import interfaces.pacmancomponents.RobotOptionPane;
import pacmansystem.ai.RobotController;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.fysicalRobot.PanelLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.totalCrashException;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import pacmansystem.ai.robot.simulatedRobot.SimulationConnection;
import pacmansystem.ai.robot.simulatedRobot.StandardSimulationBuilder;
import pacmansystem.ai.robot.simulatedRobot.Robot.Simulation;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
import data.enums.Orientation;
import data.world.OwnRobotData;
import data.world.RealWorld;
import data.world.RobotData;
import data.world.World;

public class DistributedMain
{

	private static int robotNumber;
	private static String robotName;
	private static boolean isSimulated;
	private static World world = World.getWorldWithRabbbitMQ();
	private static boolean visible = true;
	private static int speed = 100;
	//name number simulated visibible speed txt
	public static void main(String[] args)
	{
		//ComponentFrame.showFrame("RabbitMQ", new RabbitHistory());
		if (!goodArgs(args)) {
			System.out.println("DistributedMain invoked without (correct) arguments");
			RobotOptionPane robotOptions = new RobotOptionPane();
			robotName = robotOptions.getRobotName();
			robotNumber = robotOptions.getRobotNumber();
			isSimulated = robotOptions.isSimulatedRobot();
			printRobotInfo();
		} else {
			
			robotName = args[0];
			robotNumber = new Integer(args[1]);
			isSimulated = new Boolean(args[2]);
			visible = new Boolean(args[3]);
			printRobotInfo();
		}
		RobotController robot;
		if (args.length < 5)
			robot = initNewRobot();
		else{
			speed = 1500-new Integer(args[4]);
			robot = initSimulated(new Integer(args[5]),args[6]);
		}
		// ComponentFrame.showFrame("RabbitMQ", new RabbitHistory());

		Mainscreen gui = new Mainscreen();
		if (visible)
			gui.start(); 
		robot.establishConnection();
		gui.setRobotData(robot.getOwnData());
		for (RobotData r : robot.getWorld().get_robots().values()){
			if (!r.getName().equals(robot.getOwnData().getName()))
				gui.setRobotData(r);
		}
		
		while(true){
			try{
				//gui.setWorld(robot.getWorld());
				robot.start();
			}catch(totalCrashException e){
				System.err.println("ROBUUUSTNESSS!!!");
				((OwnRobotData) robot.getWorld().getRobot(robot.getName())).resetAfterCrash();
//				PanelLayerInterface layer = robot.getPathLayer().getOrientationLayer().getLayer();
//				OrientationLayer orlayer = new OrientationLayer(layer);
//				robot = new RobotController(orlayer, robotName, world);
			}
		}
	}

	/**
	 * Prints the info of the created robot.
	 */
	private static void printRobotInfo() {
		if (robotName == null)
			throw new IllegalStateException("This print robot method was invoked before the robots parameters where given.");
		System.out.println("DistributedMain started:");
		System.out.println("Robot name: " + robotName);
		System.out.println("Robot starting position number: " + robotNumber);
		if (isSimulated)
			System.out.println("This robot is simulated");
		else
			System.out.println("This robot is physical");
	}

	private static boolean goodArgs(String[] args)
	{

		return args.length >= 4;
	}

	private static RobotController initNewRobot()
	{
		RobotController controller;

		if (!isSimulated) {
			MoverLayer ml = new MoverLayer();
			PanelLayer pl = new PanelLayer(ml);
			OrientationLayer ol = new OrientationLayer(pl);
			controller = new RobotController(ol, robotName, world);
			return controller;
		} else {
//			RealWorld simulatorWorld = RealWorld.getRealWorld();
//			PanelLayerInterface p = new SimulatedRobot(simulatorWorld,
//					simulatorWorld.getStartingPoint(robotNumber),
//					Orientation.random(),1000);
//			OrientationLayer directionlayer = new OrientationLayer(p);
//			controller = new RobotController(directionlayer, robotName, world);
			Ticker ticker = new Ticker();
			StandardSimulationBuilder builder = new StandardSimulationBuilder();
			SimulationConnection conn = new SimulationConnection();
			builder.setTicker(ticker);
			RealWorld realworld = RealWorld.getRealWorld();
			builder.setRealWorld(realworld);
			builder.setSimulationConnection(conn);
			System.out.println(realworld.getStartingPoint(robotNumber));
			builder.setStartLocation(realworld.getStartingPoint(robotNumber));
			Simulation sim =builder.build();
			sim.start();
			MoverLayer ml = new MoverLayer(conn);
			PanelLayer pl = new PanelLayer(ml);
			OrientationLayer ol = new OrientationLayer(pl);
			controller = new RobotController(ol, robotName, world);
			return controller;
		}

	}

	private static RobotController initSimulated(int integer, String string)
	{
		RealWorld simulatorWorld = RealWorld.getRealWorld(string);
		PanelLayerInterface p = new SimulatedRobot(simulatorWorld,
				simulatorWorld.getStartingPoint(robotNumber),
				Orientation.fromOrdinal(integer),speed);
		OrientationLayer directionlayer = new OrientationLayer(p);
		return new RobotController(directionlayer, robotName, world);
	}
	
}
