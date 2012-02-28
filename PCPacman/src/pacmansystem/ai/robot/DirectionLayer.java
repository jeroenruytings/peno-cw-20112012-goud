package pacmansystem.ai.robot;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Direction;
import pacmansystem.board.enums.Orientation;

public class DirectionLayer
{
	private Orientation currentOrientation;

	private PanelLayerInterface layer;

	/**
	 * 
	 * @param layer
	 */
	public DirectionLayer(PanelLayerInterface layer)
	{
		this.layer = layer;
	}

	public void go(Orientation... o) throws IllegalDriveException
	{
		for (Orientation orient : o)
			go(orient);
	}

	public Panel getPanel()
	{
		return layer.getPanel();
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