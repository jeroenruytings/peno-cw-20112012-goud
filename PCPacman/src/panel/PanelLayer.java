package panel;

import javax.naming.OperationNotSupportedException;

import direction.Direction;

public class PanelLayer {
	
	public void go(Direction d)
	{
	
	}
	public boolean hasBorder(Direction dir){
		return false;
		
	}
	/**
	 * Checks if this panel has a barcode
	 * @return true if there is a panel on this barcode
	 */
	public boolean hasBarcode()
	{
		//TODO: implement;
		return false;
	}
	//returns null if there is no barcode < also no ints.
	/**
	 * 
	 * @return null if this.hasBarcode() == false;
	 * @throws OperationNotSupportedException 
	 */
	public Barcode getBarcode() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException();
	}
	public Direction getPacman()
	{
		return null;
	}
}
