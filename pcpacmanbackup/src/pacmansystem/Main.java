package pacmansystem;

import interfaces.mainscreen.Mainscreen;
import interfaces.mainscreen.ModeChooser;
import interfaces.pacmancomponents.RobotType;
import pacmansystem.ai.RobotController;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.fysicalRobot.PanelLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import data.enums.Orientation;
import data.world.RealWorld;
import data.world.World;

public class Main
{

	RealWorld simulatorWorld = null;
	public Main()
	{
		ModeChooser mc = new ModeChooser();
		
		RobotType[] choice = mc.getChoice();
		RobotController[] robot = new RobotController[4];
		simulatorWorld = RealWorld.getRealWorld();
		for (int i = 0; i < choice.length; i++) {
			RobotType robotType = choice[i];
			if (robotType == RobotType.PHYSICALROBOT)
				robot[i] = initNewRobot(robotType);
			else if (robotType == RobotType.VIRTUALROBOT){
				robot[i] = initNewRobot(robotType);
			}
		}

		
		
		for(RobotController c:robot)
			c.start();
		Mainscreen gui = new Mainscreen();

		for (RobotController r : robot)
			gui.setRobotData(r.getData());
		
		gui.setWorld(robot[0].getWorld());
		gui.start();
	}
	void gogo(final RobotController c)
	{
		new Thread(new Runnable()
		{			
			@Override
			public void run()
			{
				//c.join();
				//c.sendName();
				c.explore();
			}
		}).start();
	}

	private RobotController initNewRobot(RobotType robotType) {
		RobotController controller;
		switch (robotType) {
		case PHYSICALROBOT:
			MoverLayer ml = new MoverLayer();
			PanelLayer pl = new PanelLayer(ml);
			OrientationLayer ol = new OrientationLayer(pl);
			controller = new RobotController(ol,"Goud" + Math.random(), new World());
			return controller;

		case VIRTUALROBOT:
			PanelLayerInterface p= new SimulatedRobot(getSimulatorWorld(),getSimulatorWorld().getStartingPoint(), Orientation.random());
			OrientationLayer directionlayer = new OrientationLayer(p);
			controller = new RobotController(directionlayer, "Goud" + Math.random(), new World());
			return controller;
		}
		throw new IllegalStateException("One of the robots is not initialized.");
		
	}

	private RealWorld getSimulatorWorld() {
		return simulatorWorld;
	}

	public static void main(String[] args)
	{
		new  Main();
	}
	
}
