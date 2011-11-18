import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class BarcodeThread implements java.lang.Runnable {

	static LightSabre l = null;
	static LightSensor sensor = new LightSensor(SensorPort.S3);
	node[] buffer = new node[20];
	int elems = 0;
	private long samplingtime=5;
	static int WHITE;
	static int BLACK;
	static int BROWN;
	static int noiseDist = 24;

	private static void calibrateBlack() {
		System.out.println("calibrate black:");
		Button.waitForPress();
		BarcodeThread.BLACK = SensorPort.S3.readRawValue();
	}

	private static void calibrateWhite() {
		System.out.println("calibrate white:");
		Button.waitForPress();
		BarcodeThread.WHITE = SensorPort.S3.readRawValue();

	}

	private static void calibrateBrown() {
		System.out.println("calibrate brown:");
		Button.waitForPress();
		BarcodeThread.BROWN = SensorPort.S3.readRawValue();

	}

	public static void calibrate() {
		calibrateBlack();
		calibrateBrown();
		calibrateWhite();
		LCD.clear();
		System.out.println("Calibrating done");
		Button.waitForPress();
		LCD.clear();
	}

	class node {
		color c;
		int base;

		public node(color c, int b) {
			this.base = b;
			this.c = c;
		}

		/**
		 * return distance in mm
		 * 
		 * @param n
		 * @return
		 */
		float distanceto(node n) {
			return (float) ((float) (n.base - base) * Math.PI * 56f / 360f);
		}
	};

	enum barcode {
		zero(0, 0, 0, 0, 0, 0, 0), one(0, 0, 0, 1, 1, 1, 1), two(0, 0, 1, 0, 1,
				1, 0), three(0, 0, 1, 1, 0, 0, 1), four(0, 1, 0, 0, 1, 0, 1), five(
				0, 1, 0, 1, 0, 1, 0), six(0, 1, 1, 0, 0, 1, 1), seven(0, 1, 1,
				1, 1, 0, 0), eight(1, 0, 0, 0, 0, 1, 1), nine(1, 0, 0, 1, 1, 0,
				0), a(1, 0, 1, 0, 1, 0, 1), b(1, 0, 1, 1, 0, 1, 0), c(1, 1, 0,
				0, 1, 1, 0), d(1, 1, 0, 1, 0, 0, 1), e(1, 1, 1, 0, 0, 0, 0), f(
				1, 1, 1, 1, 1, 1, 1);

		private final int[] code;

		private static int hammingdistance(int[] code, int[] other) {
			int totald = 0;
			for (int i = 0; i < other.length; i++) {
				if (code[i] == other[i])
					totald++;
			}
			return totald;
		}

		public static barcode toBarcode(int[] code) {
			barcode bar = null;
			int dis = 0;
			if (code.length != barcode.one.code.length)
				return null;
			for (barcode b : barcode.values()) {
				if (dis < hammingdistance(code, b.code)) {
					dis = hammingdistance(code, b.code);
					bar = b;
				}

			}
			return bar;
		}

		public static String toString(barcode b) {
			switch (b) {
			case zero:
				return "zero";
			case one:
				return "one";
			case two:
				return "two";
			case three:
				return "three";
			case four:
				return "four";
			case five:
				return "five";
			case six:
				return "six";
			case seven:
				return "seven";
			case eight:
				return "eight";
			case nine:
				return "nine";
			case a:
				return "a";
			case b:
				return "b";
			case c:
				return "c";
			case d:
				return "d";
			case e:
				return "e";
			case f:
				return "f";
			default:
				return "";
			}
		}

		barcode(int... arg) {
			code = arg;
		}
	};

	enum color {
		white, black, brown;
		public String toString() {
			switch (this) {
			case white:
				return "white";
			case black:
				return "black";
			default:
				return "brown";
			}

		}

	}

	void push(node n, node[] stack) {
		stack[elems++] = n;
	}

	public static boolean EQual(int[] code, int[] code2) {
		for (int i = 0; i < code2.length; i++) {
			if (code[i] != code2[i])
				return false;
		}
		return true;
	}

	node pop(node[] stack) {
		return stack[elems-- - 1];
	}

	void clear(node[] stack) {
		for (int i = 0; i < stack.length; i++) {
			stack[i] = null;
			elems = 0;
		}
	}

	public void start() {
		Thread barCodeReader = new Thread(this);
		barCodeReader.start();
	}

	//@Override
	public void run() {
		sensor.setFloodlight(true);
		color currentcolor = color.brown;
		while (true) {
			try{
				Thread.sleep(samplingtime);
			}catch(Exception e)
			{
				
			}
			color c = getCurrentColor();
			if (!currentcolor.equals(c)) {
				currentcolor = c;
				push(new node(c, Motor.A.getTachoCount()), buffer);
				if (noise() && !currentcolor.equals(color.brown)) {

					node P = pop(buffer);
					node brown = pop(buffer);

					if (peek(buffer).c.equals(currentcolor)) {
						;
					} else {
						P.base = brown.base;
						push(P, buffer);
					}
				}
			}
			if (elems > 2
					&& peek(buffer).c.equals(color.brown)
					&& peek(buffer).distanceto(
							new node(null, Motor.A.getTachoCount())) > noiseDist) {
				// new code detected 
				int[] code = convertTocode(buffer);
				for (int i=0; i<code.length; i++){
					LCD.drawInt(code[i], 0, i);
				}
				barcode t = barcode.toBarcode(code);
				lastRead = t;
				//lastRead = t;
				//editLastRead(t);
				clear(buffer);
				setNewBarcode(true);
				currentcolor = color.brown;
			}
		}// while true

	}
	
	private static boolean newBarcode=false;

	public boolean hasNewBarcode() {
		return BarcodeThread.newBarcode;
	}

	public void setNewBarcode(boolean newBarcode) {
		BarcodeThread.newBarcode = newBarcode;
	}

	@SuppressWarnings("unused")
	private void printbuffer() {
		for (int i = 0; i < elems; i++)
			System.out.println("N:" + i + " c:" + buffer[i].c.toString() + ":"
					+ buffer[i].base);
	}

	@SuppressWarnings("unused")
	private void PAUSE() {
		Motor.A.stop();
		Motor.B.stop();
		Button.waitForPress();
		Motor.B.forward();
		Motor.A.forward();
	}

	public static double todegrees(long cm) {
		return cm / Math.PI / 56 * 3600;
	}

	private node peek(node[] buffer2) {
		return buffer2[elems - 1];

	}

	private boolean noise() {
		if (elems <= 1)
			return false;
		return buffer[elems - 2].c.equals(color.brown)
				&& buffer[elems - 2].distanceto(buffer[elems - 1]) < noiseDist;
	}

	private int[] convertTocode(node[] buffer2) {
		@SuppressWarnings("unused")
		boolean castup = true;
		double totaldist = buffer2[elems - 1].base - buffer2[0].base;
		double basedist = totaldist / 7; // there are 7 bits
		int[] returnvalue = new int[7];
		int filled = 0;
		for (int i = 1; i < elems; i++) {
			double dist = buffer2[i].base - buffer2[i - 1].base;
			color c = buffer2[i - 1].c;
			long UNITS = 0;
			UNITS = Math.round((dist / basedist));

			int j = filled;

			for (; j < filled + UNITS; j++) {
				switch (c) {
				case black:
					returnvalue[j] = 0;
					break;
				case white:
					returnvalue[j] = 1;
					break;
				}
			}
			filled = j;

		}
		return returnvalue;
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

	static color getc(int v) {
		if (iswhite(v))
			return color.white;
		if (isblack(v))
			return color.black;
		return color.brown;
	}

	color getCurrentColor() {
		return getc(SensorPort.S3.readRawValue());
	}

//	private barcode lastRead=null;

//	private barcode editLastRead(barcode b) {
//		if (b == null) {// external read
//			if(lastRead==null)
//			{
//				return null;
//			}
//			System.out.println("now something fancy should happen");
//			barcode t = lastRead;
//			return t;
//		}//internal write
//		lastRead = b;
//		System.out.println(lastRead +" has been written");
//		return null;
//	}

//	public barcode getBarcode() {
//		return editLastRead(null);
//	}
	
	
	private static barcode lastRead;
	/**
	 * non destructive, will always return the barcode that was lastread.
	 * @return
	 */
	public int getBarcode(){
		//return SensorPort.S4.readRawValue();
		
		if(lastRead == null)
			return 666;
		return lastRead.ordinal();
//		if (lastRead.toString().equals("zero"))
//			return 0;
//		if (lastRead.toString().equals("one"))
//			return 1;
//		if (lastRead.toString().equals("two"))
//			return 2;
//		if (lastRead.toString().equals("three"))
//			return 3;
//		if (lastRead.toString().equals("four"))
//			return 4;
//		if (lastRead.toString().equals("five"))
//			return 5;
//		if (lastRead.toString().equals("six"))
//			return 6;
//		if (lastRead.toString().equals("seven"))
//			return 7;
//		if (lastRead.toString().equals("eight"))
//			return 8;
//		if (lastRead.toString().equals("nine"))
//			return 9;
//		if (lastRead.toString().equals("a"))
//			return 10;= true;
		//}			
//		if (lastRead.toString().equals("b"))
//			return 11;
//		if (lastRead.toString().equals("c"))
//			return 12;
//		if (lastRead.toString().equals("d"))
//			return 13;
//		if (lastRead.toString().equals("e"))
//			return 14;
//		if (lastRead.toString().equals("f"))
//			return 15;
//		return 666;
	}


}