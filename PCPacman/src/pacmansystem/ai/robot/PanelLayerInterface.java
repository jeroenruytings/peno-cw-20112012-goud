package pacmansystem.ai.robot;

import java.awt.Point;
import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import util.board.Panel;
import util.enums.Direction;
import util.enums.Orientation;

public interface PanelLayerInterface
{

	public abstract void go(Direction d) throws IllegalDriveException;

	public abstract boolean hasBorder(Direction d) throws IOException;

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
	 */
	public abstract Barcode getBarcode() throws OperationNotSupportedException;

	/**
	 * Return estimated location of pacman relative to your position (you are at 0,0 and NORTH is forward)
	 * 
	 * @return
	 */
	public abstract boolean getPacman();

	public abstract Panel getPanel(Orientation currentOrientation);

}