package pacmansystem.ai.robot;

import java.awt.Point;

import javax.naming.OperationNotSupportedException;

import pacmansystem.ai.robot.BarcodeReader.color;
import pacmansystem.ai.robot.fysicalRobot.connector.VirtuBot;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Direction;





public class PanelLayer implements PanelLayerInterface {
	
	VirtuBot virtu;
	public final static int distance = 40;
	
	public PanelLayer(VirtuBot virtu) {
		this.virtu = virtu;
	}
	
	/* (non-Javadoc)
	 * @see panel.PanelLayerInterface#go(direction.Direction)
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see panel.PanelLayerInterface#hasBorder(direction.Direction)
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see panel.PanelLayerInterface#hasBarcode()
	 */
	@Override
	public boolean hasBarcode()
	{
		color color = BarcodeReader.getc(virtu.getLightSensor());
		if (color != color.brown)
			return true;
		else return false;
	}
	//returns null if there is no barcode < also no ints.
	/* (non-Javadoc)
	 * @see panel.PanelLayerInterface#getBarcode()
	 */
	@Override
	public Barcode getBarcode() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException();
	}
	
	
	/* (non-Javadoc)
	 * @see panel.PanelLayerInterface#getPacman()
	 */
	@Override
	public Point getPacman()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see panel.PanelLayerInterface#getPanel()
	 */
	@Override
	public Panel getPanel() {

		return null;
	}
}
