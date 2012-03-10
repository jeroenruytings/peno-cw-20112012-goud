package pacmansystem.ai.robot;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import util.board.Panel;
import util.enums.Direction;
import util.enums.Orientation;

public class OrientationLayer
{
	private Orientation currentOrientation = Orientation.NORTH;

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
	
//	public OrientationLayer(RealWorld realworld){
//		layer = new SimulatedRobot(realworld);
//	}

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

	public Orientation getOrientation()
	{
		return currentOrientation;
	}

}