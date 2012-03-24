package pacmansystem.ai.robot.simulatedRobot;

import interfaces.mainscreen.Mainscreen;

import java.awt.Point;

import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Direction;
import data.enums.Orientation;
import data.world.RealWorld;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.PanelLayerInterface;

public class SimulatedRobot implements PanelLayerInterface
{
	private int speed;
	private Point _currentLocation;
	private Orientation _currentRealOrientation;
	private RealWorld _realWorld;
	private Orientation _startOrient;
	public SimulatedRobot(RealWorld realworld, Point startLocation,
			Orientation startOrient)
	{
		_realWorld = realworld;
		_currentLocation = startLocation;
		_currentRealOrientation = startOrient;
		_startOrient = _currentRealOrientation;
	}

	@Override
	public void go(Direction d) throws IllegalDriveException
	{
		sleep(3);
		if (_realWorld.getGlobalBoard().getPanelAt(_currentLocation)
				.hasBorder(_currentRealOrientation.addTo(d)))
			throw new IllegalDriveException();
		_currentLocation = _currentRealOrientation.addTo(d).addTo(
				_currentLocation);
		_currentRealOrientation = _currentRealOrientation.addTo(d);

	}

	@Override
	public WallState hasBorder(Direction d)
	{
		sleep(1);
		if (_realWorld.getGlobalBoard().getPanelAt(_currentLocation)
				.hasBorder(_currentRealOrientation.addTo(d)))
			return WallState.WALL;
		else {
			return WallState.PASSAGE;
		}
	}

	public SimulatedRobot(RealWorld simulatorWorld, Point startingPoint,
			Orientation random, int speed)
	{

		this(simulatorWorld, startingPoint, random);
		this.speed = speed;
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
		sleep(1);//this is probably wrong...
		return getPanel(getOrientation()).getBarcode();
	}

	@Override
	public boolean getPacman()
	{
		sleep(1);
		for (Direction dir : Direction.values()) {
			if (dir.equals(Direction.DOWN)) {
				continue;
			}
			Orientation orientation = _currentRealOrientation.addTo(dir);

			if (_realWorld.getGlobalBoard().wallBetween(_currentLocation,
					orientation))
				continue;

			else if (_realWorld.getPacmanLocation().equals(
					orientation.addTo(_currentLocation))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Panel getPanel(Orientation currentOrientation)
	{
		sleep(2);
		Panel rv = _realWorld.getGlobalBoard().getPanelAt(_currentLocation);
		Panel returnValue = new Panel();

		for (Direction d : Direction.values()) {
			returnValue.setBorder(currentOrientation.addTo(d),
					rv.getWallState(_currentRealOrientation.addTo(d)));
		}
		if (rv.hasBarcode()) {
			if (_currentRealOrientation.equals(rv.getBarcodeOrientation()))
				returnValue.setBarcode(rv.getBarcode());
			else
				returnValue
						.setBarcode(new Barcode(rv.getBarcode().getReverse()));

			returnValue.setBarcodeOrientation(currentOrientation);
		}
		return returnValue;
	}

	private void sleep(int sec)
	{
		try {
			Thread.sleep(sec * speed);
		} catch (Exception e) {
			// silence will come
		}
	}

	@Override
	public void correctToMiddle()
	{
		// niet nodig voor virtuele robot

	}

	public static void main(String[] args)
	{
		RealWorld board = Mainscreen.getRealWorld();
		SimulatedRobot r = new SimulatedRobot(board, new Point(2, 1),
				Orientation.WEST);
		System.out.println(new Barcode(237).getBitString());
		System.out.println(r.getPanel(Orientation.EAST).getBarcode()
				.getBitString());

	}
	private Orientation getOrientation()
	{
		Direction d = Direction.diff(_startOrient, _currentRealOrientation);
		return Orientation.NORTH.addTo(d);
	}

}
