package pacmansystem;

import interfaces.mainscreen.Mainscreen;
import interfaces.pacmancomponents.RobotOptionPane;

import data.enums.Orientation;
import data.world.RealWorld;
import data.world.RobotData;
import data.world.World;

import pacmansystem.ai.RobotController;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.fysicalRobot.PanelLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;

public class DistributedMain {
	
	private static int robotNumber;
	private static String robotName;
	private static boolean isSimulated;
	private static World world;
	
	public static void main(String[] args) {

		RobotOptionPane robotOptions = new RobotOptionPane();
		robotName = robotOptions.getRobotName();
		robotNumber = robotOptions.getRobotNumber();
		isSimulated = robotOptions.isSimulatedRobot();
		RobotController robot = initNewRobot();
		
		//ComponentFrame.showFrame("RabbitMQ", new RabbitHistory());

		Mainscreen gui = new Mainscreen();
		gui.start();
		robot.establishConnection();
		for (RobotData r : robot.getWorld().get_robots().values())
			gui.setRobotData(r);

				gui.setWorld(robot.getWorld());
				robot.start();
	}
	
	private static RobotController initNewRobot() {
		RobotController controller;

		if (!isSimulated){
			MoverLayer ml = new MoverLayer();
			PanelLayer pl = new PanelLayer(ml);
			OrientationLayer ol = new OrientationLayer(pl);
			controller = new RobotController(ol,robotName, world);
			return controller;
		}
		else{
			RealWorld simulatorWorld = Mainscreen.getRealWorld();
			PanelLayerInterface p= new SimulatedRobot(simulatorWorld,simulatorWorld.getStartingPoint(robotNumber), Orientation.random());
			OrientationLayer directionlayer = new OrientationLayer(p);
			controller = new RobotController(directionlayer, robotName, world);
			return controller;
		}

	}
}
