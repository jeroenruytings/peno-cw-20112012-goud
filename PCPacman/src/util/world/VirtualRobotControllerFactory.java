package util.world;

import java.awt.Point;
import java.io.IOException;

import communicator.be.kuleuven.cs.peno.MessageSender;

import pacmansystem.ai.RobotController;
import pacmansystem.ai.robot.OrientationLayer;
import pacmansystem.ai.robot.PathLayer;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import util.enums.Orientation;

public class VirtualRobotControllerFactory {
	
	private String name;
	private MessageSender _sender;
	public Boolean lock;
	private final RealWorld _realworld;
	private final Point _startLocation;
	private final Orientation _startOrient;
	public VirtualRobotControllerFactory(String name,MessageSender sender,RealWorld realworld, Point startPoint,Orientation startOrient)  {
		this.name = name;
		this._sender=sender;
		this._realworld=realworld;
		this._startLocation = startPoint;
		this._startOrient = startOrient;
	}
	/**
	 * Shit gets done!
	 * Robotcontrolelr is created, world is created & the following things are true
	 * 4 joins have been received on the rabbit mq
	 * the world object has 4 robotdata objects 
	 * 
	 * @return
	 */
	public RobotController create() {
		
		RobotData2 data = new RobotData2(_sender);
		data.setName(name);
		World world = new World();
		world.addRobot(data, name);
		world.waitForJoins(this);
		try{
		data.send("JOIN\n");
		}catch (IOException e) {
			throw new Error(" Join command can not be executed  ");
		}
			try {
				lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				data.send(name+" NAME \n");
			} catch (IOException e) {
			throw	 new Error(" Join command can not be executed  ");
			}
			SimulatedRobot sim = new SimulatedRobot(_realworld, _startLocation, _startOrient);
			OrientationLayer orientationLayer = new OrientationLayer(sim, data);
			orientationLayer.setData(data);
			PathLayer pathlayer = new PathLayer(orientationLayer, data);
			RobotController rc = new RobotController(pathlayer,data);
			
		return rc;
	}
	
	
}
