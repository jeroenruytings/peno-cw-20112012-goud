package pacmansystem.ai.robot.fysicalRobot.connector;

import pacmansystem.ai.robot.BarcodeReader;
import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.ai.robot.fysicalRobot.barcode.ColorTransitionStack;

public class MoverLayer
{

	private int ultrasonic;
	private int lightSensor;
	private boolean pushSensor;
	private int infraredSensorDirection;
	private int infraRedSensorValue;
	private PCCommunicator pcc;
	boolean button = false;
	private int tachoCount;
	private ColorTransitionStack _colorStack = new ColorTransitionStack(this);
	
	public MoverLayer()
	{
		initialiseMoverLayer();
	}
	
	private void initialiseMoverLayer(){
		
		pcc = new PCCommunicator(this);
		Thread communicator = new Thread(pcc);
		communicator.start();
		calibrateColors();
		
	}
	

	private void calibrateColors()
	{
		calibrateBlack();
		calibrateBrown();
		calibrateWhite();
		
	}

	/**
	 * 
	 * @param degrees (negative = left)
	 */
	public void turn(int degrees)
	{
		if (degrees>=0){
			pcc.sendCommando(new Commando(Action.RIGHT,degrees,""));
		}
		else{
			pcc.sendCommando(new Commando(Action.LEFT, (-1*degrees), ""));
		}

	}

	public void drive(int distance)
	{
		if (distance>=0){
			pcc.sendCommando(new Commando(Action.FORWARD, distance, ""));
		}
		else{
			pcc.sendCommando(new Commando(Action.BACKWARD, (-1*distance), ""));
		}
	}
	
	/**
	 * 
	 * @param degrees (negative = right)
	 */
	public void turnHead(int degrees)
	{
		if (degrees>=0){
			pcc.sendCommando(new Commando(Action.HEADRIGHT,degrees,""));
		}
		else{
			pcc.sendCommando(new Commando(Action.HEADLEFT, (-1*degrees), ""));
		}
	}

	

	public int getUltrasonic()
	{
		return ultrasonic;
	}

	public void setUltrasonic(int ultrasonic)
	{
		this.ultrasonic = ultrasonic;
	}

	public int getLightSensor()
	{
		return lightSensor;
	}

	public boolean isPushSensor()
	{
		return pushSensor;
	}

	public void setPushSensor(boolean pushSensor)
	{
		this.pushSensor = pushSensor;
	}

	public int getInfraredSensorDirection()
	{
		return infraredSensorDirection;
	}

	public void setInfraredSensorDirection(int infraredSensor)
	{
		this.infraredSensorDirection = infraredSensor;
	}

	public int getTachoCount()
	{
		//TODO set tachocount
		return this.tachoCount;
		
	}

	public void calibrateBlack()
	{
		System.out.println("Sending to calibrate BLACK");
		pcc.sendCommando(new Commando(Action.CALIBRATEBLACK,0, "Calibrate black"));
		while(!buttonIsPushed()) ;
		this._colorStack.calibrate(PanelColor.BLACK, getLightSensor());
		button = false;
	}

	public ColorTransitionStack getBarcodeReader() {
		return _colorStack;
	}

	public void calibrateWhite() {
		pcc.sendCommando(new Commando(Action.CALIBRATEWHITE,0, "Calibrate white"));
		while(!buttonIsPushed()) ;
		this._colorStack.calibrate(PanelColor.WHITE, getLightSensor());
		button = false;
		
	}
	
	public void calibrateBrown() {
		pcc.sendCommando(new Commando(Action.CALIBRATEBROWN,0, "Calibrate brown"));
		while(!buttonIsPushed()) ;
		this._colorStack.calibrate(PanelColor.BROWN, getLightSensor());
		button = false;
	}
	
	public void pushButton(){
		button = true;
	}
	
	public boolean buttonIsPushed(){
		return button;
		
	}


	public void setTachoCount(Integer value) {
		this.tachoCount = value;
		
	}


	public void setLightSensor(Integer value) {
		this.lightSensor = value;
	}


	public PCCommunicator getPcc() {
		return this.pcc;
	}

	public void setInfraRedSensorValue(int infraRedSensorValue) {
		this.infraRedSensorValue = infraRedSensorValue;
	}

	public int getInfraRedSensorValue() {
		return infraRedSensorValue;
	}


}
