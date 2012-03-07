package pacmansystem;


import pacmansystem.ai.RobotController;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import util.world.RealWorld;
import util.world.World;

public class Main
{
	World world = new World();
	RealWorld w = new RealWorld();
	public Main()
	{
		init(w);
		PanelLayerInterface p= new SimulatedRobot(w);
		OrientationLayer directionlayer = new OrientationLayer(p);
		RobotController controller = new RobotController(directionlayer,world);
		
		System.out.println("succes");
	}
	private void init(RealWorld w2)
	{
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args)
	{
		
	}
	
}
