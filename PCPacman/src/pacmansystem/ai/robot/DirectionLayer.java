package pacmansystem.ai.robot;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Direction;
import pacmansystem.board.enums.Orientation;
import pacmansystem.world.RealWorld;

public class DirectionLayer
{
	private Orientation currentOrientation;

	private PanelLayerInterface layer;

	public PanelLayerInterface getLayer() {
		return layer;
	}

	/**
	 * 
	 * @param layer
	 */
	public DirectionLayer(PanelLayerInterface layer)
	{
		this.layer = layer;
	}
	
	public DirectionLayer(RealWorld realworld){
		layer = new SimulatedRobot(realworld);
	}

	public void go(Orientation... o) throws IllegalDriveException
	{
		for (Orientation orient : o)
			go(orient);
	}


	/**
	 * @throws IllegalDriveException
	 * 
	 */
	public void go(Orientation o) throws IllegalDriveException
	{
		for (Direction d : Direction.values())
			if (this.currentOrientation == o.addTo(d)) {
				layer.go(d);
				this.currentOrientation = o;
				return;
			}
	}

	public Orientation getDirection()
	{
		return currentOrientation;
	}

}