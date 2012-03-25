package pacmansystem.ai.robot.fysicalRobot;

import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Direction;
import data.enums.Orientation;
import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.fysicalRobot.connector.Action;
import pacmansystem.ai.robot.fysicalRobot.connector.Commando;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;

public class PanelLayer implements PanelLayerInterface
{

	MoverLayer mover;
	public final static int distance = 400;
	boolean isFirst = true;
	boolean hasToCorrect = false;
	

	public PanelLayer(MoverLayer mover)
	{
		this.mover = mover;
	}

	public MoverLayer getMover() {
		return mover;
	}

	public static int getDistance() {
		return distance;
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
	public WallState hasBorder(Direction d)
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
//				e.printStackTrace();
//			}
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder up: " + distanceToWall);
			if(distanceToWall == 255){	
			mover.turnHead(0);
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder up: " + distanceToWall);
			}
			if (distanceToWall < 30){
				if(distanceToWall < 15)
					hasToCorrect = true;
				System.out.println("true");
				return WallState.WALL;
			}
			else {
				System.out.println("false");
				return WallState.PASSAGE;
			}
			
		case 1:
			System.out.println("back = false");
			return WallState.PASSAGE;
			
		case 2:
			//LEFT
			mover.turnHead(-90);
			
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				e.printStackTrace();
	//		}
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder left: " + distanceToWall);
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder left: " + distanceToWall);
				}
			mover.turnHead(90);
			if (distanceToWall < 25){
				if(distanceToWall < 10)
					hasToCorrect = true;
				System.out.println("true");
				return WallState.WALL;
			}
			else{
				System.out.println("false");
				return WallState.PASSAGE;
			}
		
		case 3:
			//RIGHT
			mover.turnHead(90);
			
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder right: " + distanceToWall);
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder right: " + distanceToWall);
				}
			mover.turnHead(-90);
			if (distanceToWall < 25){
				if(distanceToWall < 15)
					hasToCorrect = true;
				System.out.println("true");
				return WallState.WALL;
			}
			else{
				System.out.println("false");
				return WallState.PASSAGE;
			}
		}
		return WallState.PASSAGE;
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
		if (color != PanelColor.BROWN){
			System.out.println("hasbarcode true");
			return true;
		}
		else{
			System.out.println("hasbarcode false");		
			return false;
		}
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
			Barcode code = mover.getBarcodeReader().searchForCode();
			if(code!=null)
				System.out.println("CODE " + code);
			else
				System.out.println("niet kunnen lezen");
			return code;
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
	public boolean getPacman()
	{
		if (mover.getInfraredSensorDirection() >0 && mover.getInfraredSensorDirection() < 10)
			return true;
		else 
			return false;
	}

	/**
	 * Geeft het paneel terug waarop de robot zich bevindt. Hij voegt muren toe en een eventuele barcode.
	 *
	 */
	@Override
	public Panel getPanel(Orientation currentOrientation)
	{
		System.out.println("correct to middle is " + hasToCorrect);
		if(hasToCorrect){
			correctToMiddle();
			hasToCorrect = false;
		}
		Panel panel = new Panel();
		for (Direction direction : Direction.values()) {

				if(!isFirst){
				
						panel.setBorder(currentOrientation.addTo(direction), hasBorder(direction));
					
				}
				else 
						panel.setBorder(currentOrientation.addTo(direction), hasFirstBorder(direction));
					
				
					
			}

		isFirst = false;
		mover.setHead(0);
		if(hasBarcode()){
			panel.setBarcode(getBarcode(), currentOrientation);
		}
		System.out.println("return panel");
		return panel;
	}
	
	public WallState hasFirstBorder(Direction direction) {
		int distanceToWall;
		switch (direction.ordinal())
		{
		case 0:
			System.out.println(mover.buttonIsPushed());
			mover.turnHead(0);
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder up: " + distanceToWall);
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder up: " + distanceToWall);
			}
			if (distanceToWall < 25){
				System.out.println("true");
				return WallState.WALL;
			}
			else {
				System.out.println("false");
				return WallState.PASSAGE;
			}
		case 1:
			mover.turn(180);
			mover.turnHead(0);
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder down: " + distanceToWall);
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder down: " + distanceToWall);
			}
			if (distanceToWall < 25){
				System.out.println("true");
				mover.turn(-180);
				return WallState.WALL;
			}
			else {
				System.out.println("false");
				mover.turn(-180);
				return WallState.PASSAGE;
			}
			
		case 2:
			//LEFT
			mover.turnHead(-90);
			
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				e.printStackTrace();
	//		}
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder left: " + distanceToWall);
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder left: " + distanceToWall);
				}
			mover.turnHead(90);
			if (distanceToWall < 25){
				System.out.println("true");
				return WallState.WALL;
			}
			else{
				System.out.println("false");
				return WallState.PASSAGE;
			}
		
		case 3:
			//RIGHT
			mover.turnHead(90);
			
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder right: " + distanceToWall);
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder right: " + distanceToWall);
				}
			mover.turnHead(-90);
			if (distanceToWall < 25){
				System.out.println("true");
				return WallState.WALL;
			}
			else{
				System.out.println("false");
				return WallState.PASSAGE;
			}
		}
		return WallState.PASSAGE;
	}

	@Override
	public void correctToMiddle(){
		if(!hasBarcode())
			mover.correctToMiddle();
	}
}
