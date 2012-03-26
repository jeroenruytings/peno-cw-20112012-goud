package pacmansystem.ai.robot.simulatedRobot.location;

import java.awt.Point;

import data.enums.Orientation;
import data.world.RealWorld;

public class LocationManager
{
	
	private Point startposition_;
	private Orientation startOrientation_;
	private RealWorld world_;

	public LocationManager(RealWorld world, Point startposition, Orientation startOrientation){
		world_=world;
		startposition_=startposition;
		startOrientation_=startOrientation;
	}
	
	
}
