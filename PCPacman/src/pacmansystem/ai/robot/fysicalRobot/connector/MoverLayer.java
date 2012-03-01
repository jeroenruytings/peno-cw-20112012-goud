package pacmansystem.ai.robot.fysicalRobot.connector;

import pacmansystem.ai.robot.BarcodeReader;

public class MoverLayer
{

	private int ultrasonic;
	private int lightSensor;
	private boolean pushSensor;
	private int infraredSensor;
	private BarcodeReader barcodeReader;
	private PCCommunicator pcc;
	boolean button = false;
	private int tachoCount;

	
	public MoverLayer()
	{
//		initialiseMoverLayer();
	}
	
	public void initialiseMoverLayer(){
		
		barcodeReader = new BarcodeReader();
//		Thread reader = new Thread(barcodeReader);
//		reader.start();
		pcc = new PCCommunicator(this);
		Thread communicator = new Thread(pcc);
		communicator.start();
		
	}
	

	public void turn(int degrees)
	{

	}

	public void drive(int distance)
	{
		pcc.sendCommando(new Commando(Action.FORWARD, "test"));
		for (int i = 0; i<100; i++);
		pcc.sendCommando(new Commando(Action.STOP, "test"));

	}

	public void turnHead(int degrees)
	{

	}

	

	public int getUltrasonic()
	{
		return ultrasonic;
	}

	private void setUltrasonic(int ultrasonic)
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

	private void setPushSensor(boolean pushSensor)
	{
		this.pushSensor = pushSensor;
	}

	public int getInfraredSensor()
	{
		return infraredSensor;
	}

	private void setInfraredSensor(int infraredSensor)
	{
		this.infraredSensor = infraredSensor;
	}

	public int getTachoCount()
	{
		//TODO set tachocount
		return this.tachoCount;
		
	}

	public void calibrateBlack()
	{
		pcc.sendCommando(new Commando(Action.CALIBRATEBLACK, "Calibrate black"));
		while(!buttonIsPushed()) ;
		getBarcodeReader().setBlack(getLightSensor());
		button = false;
	}

	public BarcodeReader getBarcodeReader() {
		return barcodeReader;
	}

	public void calibrateWhite() {
		pcc.sendCommando(new Commando(Action.CALIBRATEWHITE, "Calibrate white"));
		while(!buttonIsPushed()) ;
		getBarcodeReader().setWhite(getLightSensor());
		button = false;
		
	}
	
	public void calibrateBrown() {
		pcc.sendCommando(new Commando(Action.CALIBRATEBROWN, "Calibrate brown"));
		while(!buttonIsPushed()) ;
		getBarcodeReader().setBrown(getLightSensor());
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
		System.out.println("Dit is de lightsensorwaarde van de robot:" + value);
		this.lightSensor = value;
	}


	public PCCommunicator getPcc() {
		return this.pcc;
	}
}
