package pacmansystem.ai.robot.fysicalRobot;

import java.awt.Point;
import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.fysicalRobot.connector.Action;
import pacmansystem.ai.robot.fysicalRobot.connector.Commando;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import util.board.Panel;
import util.enums.Direction;
import util.enums.Orientation;

public class PanelLayer implements PanelLayerInterface
{

	MoverLayer mover;
	public final static int distance = 400;
	Panel panel;

	public PanelLayer(MoverLayer mover)
	{
		this.mover = mover;
		panel = new Panel();
	}

	public MoverLayer getMover() {
		return mover;
	}

	public static int getDistance() {
		return distance;
	}

	public Panel getPanel() {
		return panel;
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
			mover.drive(distance);
			break;
		case 1:
			mover.turn(180);
			mover.drive(distance);
			break;
		case 2:
			mover.turn(-90);
			mover.drive(distance);
			break;
		case 3:
			mover.turn(90);
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
			System.out.println(mover.buttonIsPushed());
			mover.turnHead(0);
			

//			try {
//				System.in.read();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder up: " + distanceToWall);
			if (distanceToWall < 40)
				return true;
			else
				return false;
			
		case 1:
			return false;
			
		case 2:
			//LEFT
			mover.turnHead(-90);
			
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
	//		}
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder left: " + distanceToWall);
			mover.turnHead(90);
			if (distanceToWall < 40)
				return true;
			else
				return false;
		
		case 3:
			//RIGHT
			mover.turnHead(90);
			
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder right: " + distanceToWall);
			mover.turnHead(-90);
			if (distanceToWall < 40)
				return true;
			else
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
		PanelColor color = mover.getColorStack().getColor(mover.getLightSensor());
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
	public Barcode getBarcode()
	{
		if(hasBarcode()){
			mover.getPcc().sendCommando(new Commando(Action.READBARCODE, 0, ""));
			while(!mover.buttonIsPushed());
			mover.releaseButton();
			return mover.getBarcodeReader().searchForCode();
		}
		else{
			return null;
		}
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
		if(hasBarcode()){
			panel.setBarcode(getBarcode());
			panel.setBarcodeOrientation(currentOrientation);
		}
		return panel;
	}
}
