package pacmansystem.ai.robot.simulatedRobot;

import java.awt.Point;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.PanelLayerInterface;
import util.board.Panel;
import util.enums.Direction;
import util.enums.Orientation;
import util.world.RealWorld;

public class SimulatedRobot implements PanelLayerInterface
{
	private Point _currentLocation;
	private Orientation _currentOrientation = Orientation.NORTH;
	private RealWorld _realWorld;

	public SimulatedRobot(RealWorld realworld, Point startLocation)
	{
		_realWorld = realworld;
		_currentLocation = startLocation;
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
	public Panel getPanel(Orientation orientation)
	{
		sleep(2);
		return _realWorld.getGlobalBoard().getPanelAt(_currentLocation);

	}

	private static void sleep(int sec)
	{
		try {
			Thread.sleep(sec * 1000);
		} catch (Exception e) {
			// silence will come
		}
	}

}
