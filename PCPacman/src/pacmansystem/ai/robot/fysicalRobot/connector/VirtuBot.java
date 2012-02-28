package pacmansystem.ai.robot.fysicalRobot.connector;

import pacmansystem.ai.robot.BarcodeReader;

public class VirtuBot
{

	private String name;
	private int ultrasonic;
	private int lightSensor;
	private boolean pushSensor;
	private int infraredSensor;
	private BarcodeReader barcodeReader;
	private PCCommunicator pcc;
	boolean button = false;

	
	public VirtuBot(String name)
	{

		setName(name);
		barcodeReader = new BarcodeReader();
		barcodeReader.calibrate(this);
		pcc = new PCCommunicator();
		Thread communicator = new Thread(pcc);
		communicator.start();
	}
	

	public void turn(int degrees)
	{

	}

	public void drive(int distance)
	{

	}

	public void turnHead(int degrees)
	{

	}

	public String getName()
	{
		return name;
	}

	private void setName(String name)
	{
		this.name = name;
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

	private void setLightSensor(int lightSensor)
	{
		this.lightSensor = lightSensor;
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
		// TODO Auto-generated method stub
		return 0;
	}

	public void calibrateBlack()
	{
		pcc.sendCommando(new Commando(Action.CALIBRATEBLACK, "Calibrate black"));
		while(!buttonIsPushed()) ;
		getBarcodeReader().setBlack(getLightSensor());
	}

		public BarcodeReader getBarcodeReader() {
			return barcodeReader;
		}

		public void calibrateWhite() {
			pcc.sendCommando(new Commando(Action.CALIBRATEWHITE, "Calibrate white"));
			while(!buttonIsPushed()) ;
			getBarcodeReader().setWhite(getLightSensor());
			
		}
		
		public void calibrateBrown() {
			pcc.sendCommando(new Commando(Action.CALIBRATEBROWN, "Calibrate brown"));
			while(!buttonIsPushed()) ;
			getBarcodeReader().setBrown(getLightSensor());
			
		}
		
		public void pushButton(){
			button = true;
		}
		
		public boolean buttonIsPushed(){
			return button;
			
		}
}
