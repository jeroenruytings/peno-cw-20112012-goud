package pacmansystem.ai.robot.fysicalRobot;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.fysicalRobot.connector.Action;
import pacmansystem.ai.robot.fysicalRobot.connector.Commando;
import pacmansystem.ai.robot.fysicalRobot.connector.CrashedException;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Direction;
import data.enums.Orientation;

public class PanelLayer implements PanelLayerInterface
{

	MoverLayer mover;
	private final static int distance = 400;
	private boolean isFirst = true;
	private boolean hasToCorrect = false;
	private Direction lastSeenDirectionPacman;
	private int lastSeenDistancePacman;
	private boolean pacmanSeen = false;
	private int lastSeenIrDistance = 0;
	

	public int getLastSeenIrDistance() {
		return lastSeenIrDistance;
	}

	public void setLastSeenIrDistance(int lastSeenIrDistance) {
		this.lastSeenIrDistance = lastSeenIrDistance;
	}

	public boolean isPacmanSeen() {
		return pacmanSeen;
	}

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
	public void go(Direction d) throws CrashedException
	{
			switch (d.ordinal())
			{
			case 0:
				try {
					mover.drive(distance);
				}catch (CrashedException e) {
					mover.setCrashed(false);
					try {
						mover.drive(-13);
					} catch (CrashedException e1) {
						//This should never happen
						e1.printStackTrace();
					}
					mover.correctToMiddle();
					throw new CrashedException();
				}
				break;
			case 1:
				mover.turn(180);
				try {
					mover.drive(distance);
				}catch (CrashedException e) {
					//This should never happen
				}
				break;
			case 2:
				mover.turn(-90);
				try {
					mover.drive(distance);
				} catch (CrashedException e) {
					mover.setCrashed(false);
					try {
						mover.drive(-13);
					} catch (CrashedException e1) {
						//This should never happen
						e1.printStackTrace();
					}
					mover.turn(90);
					mover.correctToMiddle();
					throw new CrashedException();
				}
				break;
			case 3:
				mover.turn(90);
				try {
					mover.drive(distance);
				} catch (CrashedException e) {
					mover.setCrashed(false);
					try {
						mover.drive(-13);
					} catch (CrashedException e1) {
						//This should never happen
						e1.printStackTrace();
					}
					mover.turn(-90);
					mover.correctToMiddle();
					throw new CrashedException();
				}
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
		int distanceAllowed = 40;
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
			if(mover.getInfraRedSensorValue() > 0 && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
				setLastSeenIrDistance(mover.getInfraRedSensorValue());
				pacmanSeen = true;
				if(mover.getInfraRedSensorValue() < 60){
					setLastSeenDistancePacman(3);
					
				}
				
				else if(mover.getInfraRedSensorValue() < 80){
					setLastSeenDistancePacman(2);
				}
				else{
					setLastSeenDistancePacman(1);
				}
				
				setLastSeenDirectionPacman(Direction.UP);

				
			}
			System.out.println("Hasborder up: " + distanceToWall);
			}
			if (distanceToWall < distanceAllowed){
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
			if(mover.getInfraRedSensorValue() > 0 && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
				setLastSeenIrDistance(mover.getInfraRedSensorValue());
				pacmanSeen = true;
				if(mover.getInfraRedSensorValue() < 60){
					setLastSeenDistancePacman(3);
				}
				
				else if(mover.getInfraRedSensorValue() < 80){
					setLastSeenDistancePacman(2);
				}
				else{
					setLastSeenDistancePacman(1);
				}
				
				setLastSeenDirectionPacman(Direction.LEFT);
			}
			mover.turnHead(90);
			if (distanceToWall < distanceAllowed - 7){
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
			if(mover.getInfraRedSensorValue() > 0 && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
				setLastSeenIrDistance(mover.getInfraRedSensorValue());
				pacmanSeen = true;
				if(mover.getInfraRedSensorValue() < 60){
					setLastSeenDistancePacman(3);
				}
				
				else if(mover.getInfraRedSensorValue() < 80){
					setLastSeenDistancePacman(2);
				}
				else{
					setLastSeenDistancePacman(1);
				}
				
				setLastSeenDirectionPacman(Direction.RIGHT);
			}
			mover.turnHead(-90);
			if (distanceToWall < distanceAllowed){
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
				System.out.println("barcode niet kunnen lezen");
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
	public Direction getPacmanDirection()
	{
		return getLastSeenDirectionPacman();
	}

	/**
	 * Geeft het paneel terug waarop de robot zich bevindt. Hij voegt muren toe en een eventuele barcode.
	 *
	 */
	@Override
	public Panel getPanel(Orientation currentOrientation)
	{
		pacmanSeen = false;
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
		
		System.out.println("correct to middle is " + hasToCorrect);
		if(hasToCorrect){
			correctToMiddle();
			hasToCorrect = false;
		}
		
		return panel;
	}
	
	public WallState hasFirstBorder(Direction direction) {
		int distanceAllowed = 40;
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
			
			if(mover.getInfraRedSensorValue() > 0 && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
				setLastSeenIrDistance(mover.getInfraRedSensorValue());
				pacmanSeen = true;
				System.out.println("Upinfrared: " + mover.getInfraRedSensorValue() );
				if(mover.getInfraRedSensorValue() < 60){
					setLastSeenDistancePacman(3);
				}
				
				else if(mover.getInfraRedSensorValue() < 80){
					setLastSeenDistancePacman(2);
				}
				else{
					setLastSeenDistancePacman(1);
				}
				
				setLastSeenDirectionPacman(Direction.UP);	
			}
			if (distanceToWall < distanceAllowed){
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
			if (distanceToWall < distanceAllowed){
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
			if(mover.getInfraRedSensorValue() > 0 && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
				setLastSeenIrDistance(mover.getInfraRedSensorValue());
				pacmanSeen = true;
				System.out.println("Leftinfrared: " + mover.getInfraRedSensorValue() );
				if(mover.getInfraRedSensorValue() < 60){
					setLastSeenDistancePacman(3);
				}
				
				else if(mover.getInfraRedSensorValue() < 80){
					setLastSeenDistancePacman(2);
				}
				else{
					setLastSeenDistancePacman(1);
				}
				
				setLastSeenDirectionPacman(Direction.LEFT);
			}
			mover.turnHead(90);
			if (distanceToWall < distanceAllowed -7){
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
			if(mover.getInfraRedSensorValue() > 0 && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
				System.out.println("Rightinfrared: " + mover.getInfraRedSensorValue() );
				setLastSeenIrDistance(mover.getInfraRedSensorValue());
				pacmanSeen = true;
				if(mover.getInfraRedSensorValue() < 60){
					setLastSeenDistancePacman(3);
				}
				
				else if(mover.getInfraRedSensorValue() < 80){
					setLastSeenDistancePacman(2);
				}
				else{
					setLastSeenDistancePacman(1);
				}
				
				setLastSeenDirectionPacman(Direction.RIGHT);
			}
			mover.turnHead(-90);
			if (distanceToWall < distanceAllowed){
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
	
	public void pushed() {
		//TODO: implement the crash method
	}

	public void setLastSeenDistancePacman(int lastSeenDistancePacman) throws IllegalArgumentException {
		if(lastSeenDistancePacman < 1 || lastSeenDistancePacman > 3)
			throw new IllegalArgumentException(); 
		this.lastSeenDistancePacman = lastSeenDistancePacman;
	}

	public int getLastSeenDistancePacman() {
		return lastSeenDistancePacman;
	}

	public void setLastSeenDirectionPacman(Direction lastSeenDirectionPacman) {
		this.lastSeenDirectionPacman = lastSeenDirectionPacman;
	}

	public Direction getLastSeenDirectionPacman() {
		return lastSeenDirectionPacman;
	}

	@Override
	public int getPacmanDistance() {
		return getLastSeenDistancePacman();
	}

	@Override
	public boolean pacmanSeen() {
		return pacmanSeen;
	}

}
