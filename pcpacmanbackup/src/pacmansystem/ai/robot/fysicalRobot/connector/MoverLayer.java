package pacmansystem.ai.robot.fysicalRobot.connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.BarcodeReader;
import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.ai.robot.fysicalRobot.barcode.BarCodeReader;
import pacmansystem.ai.robot.fysicalRobot.barcode.ColorTransitionStack;

public class MoverLayer extends Observable
{
	class Button{
		boolean _v=false;
		boolean get(){
			synchronized (this) {

				return _v;
			}
		}
		void set(boolean b)
		{
			synchronized (this) {
				_v=b;
			}
			
		}
	}
	private int ultrasonic;
	private int lightSensor;
	private boolean pushSensor;
	private int infraredSensorDirection;
	private int infraRedSensorValue;
	private PCCommunicator pcc;
	Button button=new Button();
	
	private int tachoCount = 1;
	private ColorTransitionStack _colorStack ;
	private BarCodeReader _reader;
	private Map<int[], Barcode> _map;
	private int headTacho = 0;
	private boolean isCrashed;
	public void setCrashed(boolean isCrashed) {
		this.isCrashed = isCrashed;
	}


	private CrashListener crashListener;
	
	public MoverLayer()
	{
		initialiseMoverLayer();
	}
	
	public MoverLayer(PCCommunicator comm)
	{
		isCrashed = false;
		pcc = comm;
		Thread communicator = new Thread(pcc);
		_colorStack = new ColorTransitionStack(this);
		_map = initbarcodes();
		_reader = new BarCodeReader(_colorStack, _map);
		crashListener = new CrashListener(this);
		this.addObserver(crashListener);
		communicator.start();
		calibrateColors();
	}
	private void initialiseMoverLayer(){
		
		isCrashed = false;
		pcc = new PCCommunicator(this);
		Thread communicator = new Thread(pcc);
		_colorStack = new ColorTransitionStack(this);
		_map = initbarcodes();
		_reader = new BarCodeReader(_colorStack, _map);
		crashListener = new CrashListener(this);
		this.addObserver(crashListener);
		communicator.start();
		calibrateColors();
		
		
	}
	

	private Map<int[], Barcode> initbarcodes() {
		Map<int[], Barcode> rv = new HashMap<int[], Barcode>();
		for(BarcodeReader.barcode bar: BarcodeReader.barcode.values()){
			int[] k = bar.getCode();
			for(int i = 0; i<k.length ; i++)
				System.out.print(k[i]);
//			System.out.println("");
//			System.out.println(bar.name().toString());
//			System.out.println("");
			try {
				rv.put(k, new Barcode(k));
			} catch (Exception e) {
				System.out.println("Probleem met map van barcodes");
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

	public void drive(int distance) throws CrashedException
	{
		if (distance>=0){
			pcc.sendCommando(new Commando(Action.FORWARD, distance, ""));
		}
		else{
			pcc.sendCommando(new Commando(Action.BACKWARD, (-1*distance), ""));
		}
		if(isCrashed){
			throw new CrashedException();
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
		releaseButton();
		pcc.sendCommando(new Commando(Action.CALIBRATEBLACK,0, "Calibrate black"));
		while(!buttonIsPushed()){
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		this._colorStack.calibrate(PanelColor.BLACK, getLightSensor());
		releaseButton();
	}

	public ColorTransitionStack getColorStack() {
		return _colorStack;
	}

	public void calibrateWhite() {
		releaseButton();
		pcc.sendCommando(new Commando(Action.CALIBRATEWHITE,0, "Calibrate white"));
		while(!buttonIsPushed()){
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		this._colorStack.calibrate(PanelColor.WHITE, getLightSensor());
		releaseButton();
		
	}
	
	public void calibrateBrown() {
		releaseButton();
		pcc.sendCommando(new Commando(Action.CALIBRATEBROWN,0, "Calibrate brown"));
		while(!buttonIsPushed()){
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		this._colorStack.calibrate(PanelColor.BROWN, getLightSensor());
		releaseButton();
	}
	
	public void releaseButton() {
		button.set(false) ;
		
	}


	public synchronized void pushButton(){
		
		button.set(true);
	}
	
	public boolean buttonIsPushed(){
		return button.get();
		
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
		while(!buttonIsPushed());
		releaseButton();
		System.out.println("is released");
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
//			System.out.println("post i " + i + degrees  );
			turnHead(degrees);
		}
		else{
			currentPosition = currentPosition - 256;
			int degrees = currentPosition - i;
//			System.out.println("neg i " + i + degrees  );
			turnHead(degrees);
		}
	}


	public void afterCrash() {
		System.out.println("AAAAAAH, ik ben gecrashed!");
		isCrashed = true;
		setChanged();
		notifyObservers();
	}


	public boolean getIsCrashed() {
		return this.isCrashed;
	}

}
