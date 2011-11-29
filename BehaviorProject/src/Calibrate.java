import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class Calibrate {

	private static LightSensor sensor = new LightSensor(SensorPort.S3);
	private static int WHITE;
	private static int BLACK;
	private static int BROWN;
	private static int noiseDist = 24;
	private static Calibrate cal = null;
	
	private Calibrate(){
		
	}
	
	public synchronized static Calibrate getInstance(){
		if (cal == null)
			cal = new Calibrate();
		return cal;
	}

	private static void calibrateBlack() {
		System.out.println("calibrate black:");
		Button.waitForPress();
		Calibrate.BLACK = SensorPort.S3.readRawValue();
		System.out.println("Black: "+ SensorPort.S3.readRawValue());
	}

	private static void calibrateWhite() {
		System.out.println("calibrate white:");
		Button.waitForPress();
		Calibrate.WHITE = SensorPort.S3.readRawValue();
		System.out.println("White: "+ SensorPort.S3.readRawValue());

	}

	private static void calibrateBrown() {
		System.out.println("calibrate brown:");
		Button.waitForPress();
		Calibrate.BROWN = SensorPort.S3.readRawValue();
		System.out.println("Brown: "+ SensorPort.S3.readRawValue());

	}

	public static void calibrate() {
		calibrateBlack();
		calibrateBrown();
		calibrateWhite();
		Button.waitForPress();
		LCD.clear();
		System.out.println("Calibrating done");
		Button.waitForPress();
		LCD.clear();
	}
	
	static boolean iswhite(int value) {
		return value < WHITE + (BROWN - WHITE) / 2;
	}

	static boolean isblack(int value) {
		return value > BLACK - (BLACK - WHITE) / 2;
	}

	static boolean isbrown(int v) {
		return !isblack(v) && !iswhite(v);
	}
}