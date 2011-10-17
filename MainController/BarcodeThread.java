import java.util.Stack;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;

public class BarcodeThread implements Runnable {
	class node {
		color c;
		int base;

		public node(color c, int b) {

		}

		float distanceto(node n) {
			return (float) ((float) (n.base - base) * Math.PI * 56f / 360f);
		}
	};

	enum barcode {
		zero(0, 0, 0, 0, 0, 0, 0), one(0, 0, 0, 1, 1, 1, 1), two(0, 0, 1, 0, 1,
				1, 0), three(0, 0, 1, 1, 0, 0, 1), four(0, 1, 0, 0, 1, 0, 1), ;

		private final int[] code;

		barcode(int... arg) {
			code = arg;
		}
	};

	node[] buffer = new node[10];
	int elems = 0;

	void push(node n, node[] stack) {
		stack[elems++] = n;
	}

	node pop(node[] stack) {
		return stack[elems--];
	}

	void clear(node[] stack) {
		for (int i = 0; i < stack.length; i++) {
			stack[i] = null;
			elems = 0;
		}
	}

	static LightSensor sensor = new LightSensor(SensorPort.S4);
	static Stack passedStrips = new Stack();

	static int samplingperiod = 10;

	public void start() {
		Thread barCodeReader = new Thread(new BarcodeThread());
		barCodeReader.start();
	}

	@Override
	public void run() {
		sensor.setFloodlight(true);
		System.out.println("callibrate low");
		Button.waitForPress();
		sensor.calibrateLow();
		LCD.clearDisplay();
		System.out.println("callibrate high");
		Button.waitForPress();
		sensor.calibrateHigh();
		color currentcolor = color.brown;
		while (true) {
			try {
				Thread.sleep(samplingperiod);
			} catch (Exception e) {
			}
			color c = getcurrentcolor();
			System.out.println(""+c.toString());
			System.out.println(SensorPort.S4.readRawValue());
			if (!currentcolor.equals(c)) {
				Button.waitForPress();
				
				currentcolor = c;
				/*push(new node(c, Motor.A.getTachoCount()), buffer);
				if (currentcolor.equals(color.brown)) {

					float dist = buffer[0].distanceto(buffer[elems - 1]);
					if (dist > 30) {
						int[] code = convertTocode(buffer);

					} else {
						clear(buffer);
					}
				}*/
			}
		}
	}

	private int[] convertTocode(node[] buffer2) {
		float totaldist = buffer2[0].distanceto(buffer2[elems - 1]);
		float basedist = totaldist / 7;// there are 7 bits
		int[] returnvalue = new int[7];
		int filled = 0;
		for (int i = 1; i < elems; i++) {
			float dist = buffer[i - 1].distanceto(buffer2[i]);
			color c = buffer[i - 1].c;
			int elems = (int) (dist / basedist);
			for (int j = filled; j < filled + elems; j++) {
				switch (c) {
				case black:
					returnvalue[j] = 0;
					break;
				case white:
					returnvalue[j] = 1;
					break;
				}
			}

		}
		return null;
	}

	static int marge = 10;

	static boolean iswhite(int value) {
		return value <620;
	}

	static boolean isblack(int g) {
		return (g >700);
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

	color getcurrentcolor() {
	
		return getc(SensorPort.S4.readRawValue());
	}
}
