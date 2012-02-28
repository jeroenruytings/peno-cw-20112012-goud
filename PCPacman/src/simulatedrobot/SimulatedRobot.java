package simulatedrobot;

import java.awt.Point;

import javax.naming.OperationNotSupportedException;

import mainController.Orientation;

import board.Panel;
import board.Simulator;
import direction.Direction;
import panel.Barcode;
import panel.PanelLayerInterface;

public class SimulatedRobot implements PanelLayerInterface {
	private Point _currentLocation;
	private Orientation _currentOrientation;
	private Simulator _realWorld;
	public SimulatedRobot(Simulator realworld)
	{
		_realWorld = realworld;
	}

	@Override
	public void go(Direction d) throws IllegalDriveException {
		if(_realWorld.getGlobalBoard().getPanelAt(_currentLocation).getBorder(_currentOrientation.addTo(d)))
			throw new IllegalDriveException();
		_currentLocation = _currentOrientation.addTo(d).addTo(_currentLocation);
		_currentOrientation = _currentOrientation.addTo(d);
	}

	@Override
	public boolean hasBorder(Direction d) {
		return _realWorld.getGlobalBoard().getPanelAt(_currentLocation).getBorder(_currentOrientation.addTo(d));
	}

	@Override
	public boolean hasBarcode() {
		return _realWorld.getGlobalBoard().getPanelAt(_currentLocation).hasBarcode();
		
	}

	@Override
	public Barcode getBarcode()  {
		return _realWorld.getGlobalBoard().getPanelAt(_currentLocation).getBarcode();
	}

	@Override
	public Point getPacman() {
		return _realWorld.getPacmanLocation();
	}

	@Override
	public Panel getPanel() {
		return null;
		
	}
	
}
