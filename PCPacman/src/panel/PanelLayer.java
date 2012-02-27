package panel;

import javax.naming.OperationNotSupportedException;

import board.Panel;

import panel.BarcodeReader.color;

import mover.VirtuBot;

import direction.Direction;

public class PanelLayer {
	
	VirtuBot virtu;
	public final static int distance = 40;
	
	public PanelLayer(VirtuBot virtu) {
		this.virtu = virtu;
	}
	
	public void go(Direction d)
	{
		switch(d.ordinal()){
		case 0: 
			virtu.turn(-90);
			virtu.drive(distance);
			break;
		case 1: 
			virtu.turn(90);
			virtu.drive(distance);
			break;
		case 2:
			virtu.drive(distance);
			break;
		case 3:
			virtu.turn(180);
			virtu.drive(distance);
		}
	}
	public boolean hasBorder(Direction d){
		int distanceToWall;
		switch(d.ordinal()){
		case 0: 
			virtu.turnHead(-90);
			distanceToWall = virtu.getSensorValue(Sensor.SONAR);
			virtu.turnHead(90);
			if(distanceToWall < 15)
				return true;
			else 
				return false;
		case 1: 
			virtu.turnHead(90);
			distanceToWall = virtu.getSensorValue(Sensor.SONAR);
			virtu.turnHead(-90);
			if(distanceToWall < 15)
				return true;
			else 
				return false;
		case 2:
			distanceToWall = virtu.getSensorValue(Sensor.SONAR);
			if(distanceToWall < 15)
				return true;
			else 
				return false;
		case 3:
			return false;
		}
		return false;		
	}
	/**
	 * @pre robot is on middlepoint of panel
	 * Checks if this panel has a barcode
	 * @return true if there is a panel on this barcode
	 */
	public boolean hasBarcode()
	{
		color color = BarcodeReader.getc(virtu.getLightSensor());
		if (color != color.brown)
			return true;
		else return false;
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

	public Panel getPanel() {

		return null;
	}
}
