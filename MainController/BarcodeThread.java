import java.util.Stack;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class BarcodeThread implements java.lang.Runnable {
	static LightSensor sensor = new LightSensor(SensorPort.S4);
	static Stack passedStrips = new Stack();

	static int samplingperiod = 10;
	node[] buffer = new node[10];
	int elems = 0;

	class node {
		color c;
		int base;

		public node(color c, int b) {
			this.base = b;
			this.c = c;
		}

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

		public static barcode toBarcode(int[] code) {
			if (code.length != barcode.one.code.length)
				return null;
			for (barcode b : barcode.values()) {
				if (EQual(b.code, code))
					return b;

			}
			return null;
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
		return stack[elems--];
	}

	void clear(node[] stack) {
		for (int i = 0; i < stack.length; i++) {
			stack[i] = null;
			elems = 0;
		}
	}

	public void start() {
		Thread barCodeReader = new Thread(new BarcodeThread());
		barCodeReader.start();
	}

	@Override
	public void run() {
		sensor.setFloodlight(true);

		color currentcolor = color.brown;
		label1: while (true) {
			try {
				Thread.sleep(samplingperiod);
			} catch (Exception e) {
			}
			color c = getCurrentColor();
			if (!currentcolor.equals(c)) {
				// Button.waitForPress();
				System.out.println("" + c.toString());

				currentcolor = c;
				push(new node(c, Motor.A.getTachoCount()), buffer);
				
				if (currentcolor.equals(color.brown)) {
					float dist = buffer[0].distanceto(buffer[elems - 1]);

					if (elems > 1) {
						float dist2 = buffer[elems - 2]
								.distanceto(buffer[elems - 1]);
						System.out.println("D: "+dist2);
							if (dist2 < 5) {
							buffer[elems - 2].base += buffer[elems - 1].base;
							elems--;
							currentcolor = buffer[elems-1].c;
							continue label1;
						}
					} else {
						if (dist > 30) {
							int[] code = convertTocode(buffer);
							System.out.println(barcode.toString(barcode
									.toBarcode(code)));
							Button.waitForPress();
							clear(buffer);
						} else {
							clear(buffer);
						}
					}
				}
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
			int UNITS = (int) (dist / basedist);
			int j=filled;
			for (; j < filled + UNITS; j++) {
				switch (c) {
				case black:
					returnvalue[j] = 0;
					break;
				case white:
					returnvalue[j] = 1;
					break;
				}
			filled= j;
			}

		}
		return null;
	}

	static int marge = 10;

	static boolean iswhite(int value) {
		return value < 620;
	}

	static boolean isblack(int value) {
		return value > 670;
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
		return getc(SensorPort.S4.readRawValue());
	}
}
