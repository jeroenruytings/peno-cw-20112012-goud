package pacmansystem.ai.robot.fysicalRobot.connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.BarcodeReader;
import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.ai.robot.fysicalRobot.barcode.BarCodeReader;
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
	private int tachoCount = 1;
	private ColorTransitionStack _colorStack ;
	private BarCodeReader _reader;
	private Map<int[], Barcode> _map;
	private int headTacho = 0;
	public MoverLayer()
	{
		initialiseMoverLayer();
	}
	
	
	private void initialiseMoverLayer(){
		
		pcc = new PCCommunicator(this);
		Thread communicator = new Thread(pcc);
		communicator.start();
		_colorStack = new ColorTransitionStack(this);
		_map = initbarcodes();
		_reader = new BarCodeReader(_colorStack, _map);
		calibrateColors();
		
		
	}
	

	private Map<int[], Barcode> initbarcodes() {
		Map<int[], Barcode> rv = new HashMap<int[], Barcode>();
		for(BarcodeReader.barcode bar: BarcodeReader.barcode.values()){
			int[] k = bar.getCode();
			System.out.println(k);
			try {
				rv.put(k, new Barcode(k));
			} catch (Exception e) {
				System.out.println("probleem met map van barcodes");
				e.printStackTrace();
			}
		}
		return rv;
	}

	private void calibrateColors()
	{
		calibrateBlack();
		calibrateBrown();
		calibrateWhite();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	 * @param degrees (negative = left)
	 */
	public void turnHead(int degrees)
	{
		if (degrees>=0){
			pcc.sendCommando(new Commando(Action.HEADRIGHT,degrees,""));
			while(!buttonIsPushed()){
			}
			releaseButton();
		}
		else{
			pcc.sendCommando(new Commando(Action.HEADLEFT, (-1*degrees), ""));
			while(!buttonIsPushed()){
			}
			releaseButton();
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
		
		return this.tachoCount;
		
	}

	public void calibrateBlack()
	{
		System.out.println("Sending to calibrate BLACK");
		pcc.sendCommando(new Commando(Action.CALIBRATEBLACK,0, "Calibrate black"));
		while(!buttonIsPushed()) ;
		this._colorStack.calibrate(PanelColor.BLACK, getLightSensor());
		releaseButton();
	}

	public ColorTransitionStack getColorStack() {
		return _colorStack;
	}

	public void calibrateWhite() {
		pcc.sendCommando(new Commando(Action.CALIBRATEWHITE,0, "Calibrate white"));
		while(!buttonIsPushed()) ;
		System.out.println("getLightSensor: " + getLightSensor());
		this._colorStack.calibrate(PanelColor.WHITE, getLightSensor());
		releaseButton();
		
	}
	
	public void calibrateBrown() {
		pcc.sendCommando(new Commando(Action.CALIBRATEBROWN,0, "Calibrate brown"));
		while(!buttonIsPushed()) ;
		this._colorStack.calibrate(PanelColor.BROWN, getLightSensor());
		releaseButton();
	}
	
	public void releaseButton() {
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
		if(_colorStack.sufficientlyCalibrated())
			_colorStack.pushColor(value, getTachoCount());
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

	public BarCodeReader getBarcodeReader() {
		return _reader;
	}
	
	public void correctToMiddle(){
		releaseButton();
		pcc.sendCommando(new Commando(Action.CORRECT, 0, ""));
		while(!buttonIsPushed()) ;
		releaseButton();
	}


	public void setHeadTacho(int value) {
		headTacho  = value;
		
	}

	public int getHeadTacho(){
		return headTacho;
	}


	public void setHead(int i) {
		int currentPosition = getHeadTacho();
		if (currentPosition<128){
			int degrees = currentPosition - i;
			System.out.println("post i " + i + degrees  );
			turnHead(degrees);
		}
		else{
			currentPosition = currentPosition - 256;
			int degrees = currentPosition - i;
			System.out.println(" neg i " + i + degrees  );
			turnHead(degrees);
		}
	}

}
