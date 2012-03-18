package pacmansystem;



import javax.swing.JOptionPane;

import interfaces.mainscreen.Mainscreen;
import pacmansystem.ai.RobotController;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.fysicalRobot.PanelLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import util.enums.Orientation;
import util.world.RealWorld;
import util.world.RobotData;

public class DistributedMain {

	public static void main(String[] args) {
			RobotController robot = initNewRobot();
			
			//ComponentFrame.showFrame("RabbitMQ", new RabbitHistory());
			robot.join();
			synchronized (robot.getWorld()) {
				try {
					// Wacht totdat er 4 joins zijn, of er een CommandName wordt uitgevoerd.
					robot.getWorld().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			robot.sendName();
			synchronized (robot.getWorld()) {
				try {
					// wacht totdat er 4 CommandName's zijn uitgevoerd.
					robot.getWorld().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Mainscreen gui = new Mainscreen();

			for (RobotData r : robot.getWorld().get_robots().values())
				gui.setRobotData(r);
			
			gui.setWorld(robot.getWorld());
			gui.start();
			start(robot);		
	}
private static RobotController initNewRobot() {
	RobotController controller;
	switch (JOptionPane.showConfirmDialog(null, "Is deze robot een gesimuleerde robot?", "Robot Modus", JOptionPane.YES_NO_OPTION)) {
	case JOptionPane.NO_OPTION:
		MoverLayer ml = new MoverLayer();
		PanelLayer pl = new PanelLayer(ml);
		OrientationLayer ol = new OrientationLayer(pl);
		controller = new RobotController(ol,true);
		return controller;

	case JOptionPane.YES_OPTION:
		RealWorld simulatorWorld = Mainscreen.getRealWorld();
		PanelLayerInterface p= new SimulatedRobot(simulatorWorld,simulatorWorld.getStartingPoint(), Orientation.random());
		OrientationLayer directionlayer = new OrientationLayer(p);
		controller = new RobotController(directionlayer, true);
		return controller;
	}
	throw new IllegalStateException("One of the robots is not initialized.");
	
}

private static void start(final RobotController c)
{
	new Thread(new Runnable()
	{
		
		@Override
		public void run()
		{
			c.explore();
		}
	}).start();
}
}
