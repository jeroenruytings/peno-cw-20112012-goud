package pacmansystem.ai.robot;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import pacmansystem.ai.robot.simulatedRobot.SimulatedRobot;
import util.board.Panel;
import util.enums.Direction;
import util.enums.Orientation;
import util.world.RealWorld;

public class OrientationLayer
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
	public OrientationLayer(PanelLayerInterface layer)
	{
		this.layer = layer;
	}
	
	public OrientationLayer(RealWorld realworld){
		layer = new SimulatedRobot(realworld);
	}

	public void go(Orientation... o) throws IllegalDriveException
	{
		for (Orientation orient : o)
			go(orient);
	}

	public Panel getPanel(Orientation orientation)
	{
		return layer.getPanel(orientation);
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