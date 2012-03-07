package pacmansystem;


import pacmansystem.ai.RobotController;
import pacmansystem.ai.robot.DirectionLayer;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import pacmansystem.world.RealWorld;
import pacmansystem.world.World;

public class Main
{
	World world = new World();
	RealWorld w = new RealWorld();
	public Main()
	{
		init(w);
		PanelLayerInterface p= new SimulatedRobot(w);
		DirectionLayer directionlayer = new DirectionLayer(p);
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
