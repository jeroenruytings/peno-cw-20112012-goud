package pacmansystem.ai.robot;

import pacmansystem.ai.robot.fysicalRobot.connector.VirtuBot;



public class BarcodeReader implements Runnable{
	
	
	node[] buffer = new node[20];
	int elems = 0;
	private long samplingtime=5;
	private VirtuBot virtu;
	static int WHITE;
	static int BLACK;
	static int BROWN;
	static int noiseDist = 24;

	private static void calibrateBlack() {
		
	}

	private static void calibrateWhite() {
		
	}

	private static void calibrateBrown() {
		
	}

	public static void calibrate() {
		calibrateBlack();
		calibrateBrown();
		calibrateWhite();		
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

	public void start(VirtuBot virtu) {
		this.virtu = virtu;
		Thread barCodeReader = new Thread(this);
		barCodeReader.start();
	}

	//@Override
	public void run() {
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
				push(new node(c, virtu.getTachoCount()), buffer);
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
							new node(null, virtu.getTachoCount())) > noiseDist) {
				// new code detected 
				int[] code = convertTocode(buffer);
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
		return BarcodeReader.newBarcode;
	}

	public void setNewBarcode(boolean newBarcode) {
		BarcodeReader.newBarcode = newBarcode;
	}

	@SuppressWarnings("unused")
	private void printbuffer() {
		for (int i = 0; i < elems; i++)
			System.out.println("N:" + i + " c:" + buffer[i].c.toString() + ":"
					+ buffer[i].base);
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
		return getc(virtu.getLightSensor());
	}	
	
	private static barcode lastRead;
	/**
	 * non destructive, will always return the barcode that was lastread.
	 * @return
	 */
	public int getBarcode(){
		
		if(lastRead == null)
			return 666;
		return lastRead.ordinal();
	}


}