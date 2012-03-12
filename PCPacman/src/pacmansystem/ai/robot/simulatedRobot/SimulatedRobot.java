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
	private Orientation _currentRealOrientation;
	private RealWorld _realWorld;


	public SimulatedRobot(RealWorld realworld, Point startLocation, Orientation startOrient)
	{
		_realWorld = realworld;
		_currentLocation = startLocation;
		_currentRealOrientation = startOrient;
	}

	@Override
	public void go(Direction d) throws IllegalDriveException
	{
		sleep(3);
		if (_realWorld.getGlobalBoard().getPanelAt(_currentLocation)
				.hasBorder(_currentRealOrientation.addTo(d)))
			throw new IllegalDriveException();
		_currentLocation = _currentRealOrientation.addTo(d).addTo(_currentLocation);
		_currentRealOrientation = _currentRealOrientation.addTo(d);
		_realWorld.setPacman(_currentLocation);
	}

	@Override
	public boolean hasBorder(Direction d)
	{
		sleep(1);
		return _realWorld.getGlobalBoard().getPanelAt(_currentLocation)
				.hasBorder(_currentRealOrientation.addTo(d));
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
	public boolean getPacman()
	{
		sleep(1);
		return (_realWorld.getPacmanLocation().equals(_currentRealOrientation.addTo(_currentLocation)));
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
			returnValue.setBorder(currentOrientation.addTo(d), rv.hasBorder(_currentRealOrientation.addTo(d)));
		}
		returnValue.setBarcodeOrientation(currentOrientation);
		return returnValue;
	}

	private static void sleep(int sec)
	{
		try {
			Thread.sleep(sec * 1000);
		} catch (Exception e) {
			// silence will come
		}
	}

	@Override
	public void correctToMiddle() {
		// niet nodig voor virtuele robot
		
	}

}
