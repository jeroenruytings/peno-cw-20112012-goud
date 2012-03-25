package pacmansystem.ai.robot;

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Direction;
import data.enums.Orientation;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;

public interface PanelLayerInterface
{

	public abstract void go(Direction d) throws IllegalDriveException;

	public abstract WallState hasBorder(Direction d) throws IOException;

	/**
	 * @pre robot is on middlepoint of panel Checks if this panel has a barcode
	 * @return true if there is a panel on this barcode
	 */
	public abstract boolean hasBarcode();

	// returns null if there is no barcode < also no ints.
	/**
	 * 
	 * @return null if this.hasBarcode() == false;
	 * @throws OperationNotSupportedException
	 * 
	 */
	public abstract Barcode getBarcode() throws OperationNotSupportedException;

	/**
	 *Returns if you have seen pacman in a orientation
	 * @return
	 */
	public abstract boolean getPacman();
	public Orientation getPacmanO();

	public abstract Panel getPanel(Orientation currentOrientation);
	
	public abstract void correctToMiddle();

}