package pacmansystem.ai.robot;

import pacmansystem.ai.robot.fysicalRobot.connector.CrashedException;
import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import data.board.Panel;
import data.enums.Direction;
import data.enums.Orientation;

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

	public void go(Orientation... o) throws IllegalDriveException, CrashedException
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
	 * @throws CrashedException 
	 * 
	 */
	public void go(Orientation o) throws IllegalDriveException, CrashedException
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
		
		return currentOrientation.addTo(getLayer().getPacmanDirection());
		}

}