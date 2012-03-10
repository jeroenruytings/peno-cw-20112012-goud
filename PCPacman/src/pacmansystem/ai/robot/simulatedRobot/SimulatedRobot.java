package pacmansystem.ai.robot.simulatedRobot;

import java.awt.Point;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.PanelLayerInterface;
import util.board.Panel;
import util.enums.Direction;
import util.enums.Orientation;
import util.world.RealWorld;
import util.world.RobotData;

public class SimulatedRobot implements PanelLayerInterface
{
	private Point _currentLocation;
	private Orientation _currentOrientation;
	private RealWorld _realWorld;
	private RobotData _robotData;

	public SimulatedRobot(RealWorld realworld, Point startLocation, Orientation startOrient)
	{
		_realWorld = realworld;
		_currentLocation = startLocation;
		_currentOrientation = startOrient;
	}

	@Override
	public void go(Direction d) throws IllegalDriveException
	{
		sleep(3);
		if (_realWorld.getGlobalBoard().getPanelAt(_currentLocation)
				.hasBorder(_currentOrientation.addTo(d)))
			throw new IllegalDriveException();
		_currentLocation = _currentOrientation.addTo(d).addTo(_currentLocation);
		_currentOrientation = _currentOrientation.addTo(d);
		_realWorld.setPacman(_currentLocation);
	}

	@Override
	public boolean hasBorder(Direction d)
	{
		sleep(1);
		return _realWorld.getGlobalBoard().getPanelAt(_currentLocation)
				.hasBorder(_currentOrientation.addTo(d));
	}

	@Override
	public boolean hasBarcode()
	{
		sleep(1);
		return _realWorld.getGlobalBoard().getPanelAt(_currentLocation)
				.hasBarcode();

	}

	@Override
	public Barcode getBarcode()
	{
		sleep(1);
		return _realWorld.getGlobalBoard().getPanelAt(_currentLocation)
				.getBarcode();
	}

	@Override
	public Point getPacman()
	{
		sleep(1);
		return _realWorld.getPacmanLocation();
	}

	@Override
	public Panel getPanel(Orientation currentOrientation)
	{
		sleep(2);
		Panel rv = _realWorld.getGlobalBoard().getPanelAt(_currentLocation);
		Panel returnValue = new Panel();
		returnValue.setBarcode(rv.getBarcode());
		for(Direction d:Direction.values())
		{
		returnValue.setBorder(currentOrientation.addTo(d), rv.hasBorder(_currentOrientation.addTo(d)));
		}
		return returnValue;
	}

	private static void sleep(int sec)
	{
		try {
			Thread.sleep(sec * 100);
		} catch (Exception e) {
			// silence will come
		}
	}

}
