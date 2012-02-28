package pacmansystem.ai.robot;

import java.awt.Point;

import javax.naming.OperationNotSupportedException;

import pacmansystem.ai.robot.simulatedRobot.IllegalDriveException;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Direction;

public interface PanelLayerInterface
{

	public abstract void go(Direction d) throws IllegalDriveException;

	public abstract boolean hasBorder(Direction d);

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
	 * Return estimated location of pacman
	 * 
	 * @return
	 */
	public abstract Point getPacman();

	public abstract Panel getPanel();

}