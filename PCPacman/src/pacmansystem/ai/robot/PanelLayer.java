package pacmansystem.ai.robot;

import java.awt.Point;

import javax.naming.OperationNotSupportedException;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Direction;
import pacmansystem.board.enums.Orientation;

public class PanelLayer implements PanelLayerInterface
{

	MoverLayer mover;
	public final static int distance = 40;
	Panel panel;

	public PanelLayer(MoverLayer mover)
	{
		this.mover = mover;
		panel = new Panel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see panel.PanelLayerInterface#go(direction.Direction)
	 */
	@Override
	public void go(Direction d)
	{
		switch (d.ordinal())
		{
		case 0:
			mover.turn(-90);
			mover.drive(distance);
			break;
		case 1:
			mover.turn(90);
			mover.drive(distance);
			break;
		case 2:
			mover.drive(distance);
			break;
		case 3:
			mover.turn(180);
			mover.drive(distance);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see panel.PanelLayerInterface#hasBorder(direction.Direction)
	 */
	@Override
	public boolean hasBorder(Direction d)
	{
		int distanceToWall;
		switch (d.ordinal())
		{
		case 0:
			mover.turnHead(-90);
			distanceToWall = mover.getUltrasonic();
			mover.turnHead(90);
			if (distanceToWall < 15)
				return true;
			else
				return false;
		case 1:
			mover.turnHead(90);
			distanceToWall = mover.getUltrasonic();
			mover.turnHead(-90);
			if (distanceToWall < 15)
				return true;
			else
				return false;
		case 2:
			distanceToWall = mover.getUltrasonic();
			if (distanceToWall < 15)
				return true;
			else
				return false;
		case 3:
			return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see panel.PanelLayerInterface#hasBarcode()
	 */
	@Override
	public boolean hasBarcode()
	{
		
		// verantwoordelijkheid van barcode reader?
		PanelColor color = mover.getBarcodeReader().getColor(mover.getLightSensor());
		if (color != color.BROWN)
			return true;
		else
			return false;
	}

	// returns null if there is no barcode < also no ints.
	/*
	 * (non-Javadoc)
	 * 
	 * @see panel.PanelLayerInterface#getBarcode()
	 */
	@Override
	public Barcode getBarcode() throws OperationNotSupportedException
	{
		throw new OperationNotSupportedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see panel.PanelLayerInterface#getPacman()
	 */
	@Override
	public Point getPacman()
	{
		return null;
	}

	/**
	 * Geeft het paneel terug waarop de robot zich bevindt. Hij voegt muren toe en een eventuele barcode.
	 *
	 */
	@Override
	public Panel getPanel(Orientation currentOrientation)
	{
		for (Direction direction : Direction.values()) {
			if(hasBorder(direction)){
				panel.setBorder(currentOrientation.addTo(direction), true);
			}
			else{
				panel.setBorder(currentOrientation.addTo(direction), false);
			}
		}
		//TODO: barcode toevoegen aan paneel
		return panel;
	}
}
