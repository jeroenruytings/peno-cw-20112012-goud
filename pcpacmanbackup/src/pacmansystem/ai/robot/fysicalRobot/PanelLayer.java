package pacmansystem.ai.robot.fysicalRobot;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.PanelLayerInterface;
import pacmansystem.ai.robot.fysicalRobot.connector.Action;
import pacmansystem.ai.robot.fysicalRobot.connector.Commando;
import pacmansystem.ai.robot.fysicalRobot.connector.CrashedException;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;
import pacmansystem.ai.robot.fysicalRobot.connector.totalCrashException;
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
	private int infraredValue = 50;
	private int counterCorrect = 1;
	

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
	public void 
	go(Direction d) throws CrashedException
	{
		hasToCorrect = true;
		counterCorrect++;
		System.out.println(d.name());
		switch (d.ordinal())
			{
			case 0:
				try {
					mover.drive(distance);
				}catch (CrashedException e) {
					mover.setCrashed(false);
					try {
						System.out.println("Case 0: 80 mm achteruit");
						mover.drive(-80);
					} catch (CrashedException e1) {
						System.out.println("case 0: crashed again");
						//This should never happen
						e1.printStackTrace();
					}
					correctToMiddle();
					throw new CrashedException();
				}
				break;
			case 1:
				mover.turn(180);
				try {
					mover.drive(distance);
				}catch (CrashedException e) {
					System.out.println("Case 1: 80 mm achteruit");

					mover.setCrashed(false);
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
						System.out.println("Case 2: 80 mm achteruit");
						mover.drive(-80);
					} catch (CrashedException e1) {
						System.out.println("case 2: crashed again");
						//This should never happen
						e1.printStackTrace();
					}
					mover.turn(90);
					correctToMiddle();
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
						System.out.println("Case 3: 80 mm achteruit");
						mover.drive(-80);
					} catch (CrashedException e1) {
						System.out.println("case 3: crashed again");
						//This should never happen
						e1.printStackTrace();
					}
					mover.turn(-90);
					correctToMiddle();
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
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder up: " + distanceToWall);
			
			if(distanceToWall == 255){	
			mover.turnHead(0);
			distanceToWall = mover.getUltrasonic();
			
			if(mover.getInfraRedSensorValue() > infraredValue && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
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
			} else
				pacmanSeen = false;
			
			if (distanceToWall < distanceAllowed){
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
			
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder left: " + distanceToWall);
			
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder left: " + distanceToWall);
				}
			
			if(mover.getInfraRedSensorValue() > infraredValue && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
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
			} else
				pacmanSeen = false;
			
			mover.turnHead(90);
			
			if (distanceToWall < distanceAllowed - 7){
				if(distanceToWall < 10)
					hasToCorrect = true;
				return WallState.WALL;
			}
			else{
				System.out.println("false");
				return WallState.PASSAGE;
			}
		
		case 3:
			//RIGHT
			mover.turnHead(90);
			
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder right: " + distanceToWall);
			
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder right: " + distanceToWall);
				}
			
			if(mover.getInfraRedSensorValue() > infraredValue && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
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
			} else
				pacmanSeen = false;
			
			mover.turnHead(-90);
			if (distanceToWall < distanceAllowed){
				if(distanceToWall < 15)
					hasToCorrect = true;
				return WallState.WALL;
			}
			else{
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
			return true;
		}
		else{	
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
			mover.getTransStack().clear();
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
		if(hasBarcode()){
			panel.setBarcode(getBarcode(), currentOrientation);
		}
		
		if(hasToCorrect && counterCorrect%5==0){
			correctToMiddle();
			hasToCorrect = false;
		}
		
		mover.setHead(0);

		
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
			
			if(mover.getInfraRedSensorValue() > infraredValue && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
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
			}else
				pacmanSeen = false;
			
			
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
				mover.turn(-180);
				return WallState.WALL;
			}
			else {
				mover.turn(-180);
				return WallState.PASSAGE;
			}
			
		case 2:
			//LEFT
			mover.turnHead(-90);
			
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder left: " + distanceToWall);
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder left: " + distanceToWall);
				}
			if(mover.getInfraRedSensorValue() > infraredValue && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
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
			}else
				pacmanSeen = false;
			
			mover.turnHead(90);
			if (distanceToWall < distanceAllowed -7){
				return WallState.WALL;
			}
			else{
				return WallState.PASSAGE;
			}
		
		case 3:
			//RIGHT
			mover.turnHead(90);
			
			distanceToWall = mover.getUltrasonic();
			System.out.println("Hasborder right: " + distanceToWall);
			if(distanceToWall == 255){	
				mover.turnHead(0);
				distanceToWall = mover.getUltrasonic();
				System.out.println("Hasborder right: " + distanceToWall);
				}
			if(mover.getInfraRedSensorValue() > infraredValue && mover.getInfraRedSensorValue() > getLastSeenIrDistance()){
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
			}else
				pacmanSeen = false;
			
			mover.turnHead(-90);
			if (distanceToWall < distanceAllowed){
				return WallState.WALL;
			}
			else{
				return WallState.PASSAGE;
			}
		}
		return WallState.PASSAGE;
	}

	@Override
	public void correctToMiddle(){
		if(!hasBarcode())
			try {
				mover.correctToMiddle();
			} catch (totalCrashException e) {
				restore();
				throw new totalCrashException();
			}
	}
	
	private void restore() {
		getMover().restore();
		
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
