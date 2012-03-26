package pacmansystem.ai.robot;

import data.board.Panel;
import data.enums.Direction;
import data.enums.Orientation;
import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;

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

	public Panel getPanel()
	{
		return layer.getPanel(currentOrientation);
	}

	/**
	 * @throws IllegalDriveException
	 * 
	 */
	public void go(Orientation o) throws IllegalDriveException
	{
		for (Direction d : Direction.values())
			if (o == currentOrientation.addTo(d)) {
				layer.go(d);
				this.currentOrientation = o;
				return;
			}
	}

	public Orientation getOrientation()
	{
		return currentOrientation;
	}
	
	public Orientation getPacman(){
		
		return currentOrientation.addTo(getLayer().getPacman());
		}

}