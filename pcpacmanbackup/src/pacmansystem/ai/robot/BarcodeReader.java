package pacmansystem.ai.robot;

import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;

public class BarcodeReader implements Runnable
{

	node[] buffer = new node[20];
	int elems = 0;
	private long samplingtime = 5;
	private MoverLayer virtu;
	private int WHITE;
	private int BLACK;
	private int BROWN;
	static int noiseDist = 24;
	
	public BarcodeReader(MoverLayer mover){
		this.virtu = mover;
		System.out.println("VLAK VOOR CALIBREREN");
	}

	public int getWHITE()
	{
		return WHITE;
	}
	
	public void setWhite(int white){
		WHITE = white;
	}
	
	public void setBlack(int black){
		BLACK = black;
	}
	
	public void setBrown(int brown){
		BROWN = brown;
	}

	public int getBLACK()
	{
		return BLACK;
	}

	public int getBROWN()
	{
		return BROWN;
	}

	private void calibrateBlack(MoverLayer virtu) {
		virtu.calibrateBlack();
	}

	private void calibrateWhite(MoverLayer virtu) {
		virtu.calibrateWhite();
	}

	private void calibrateBrown(MoverLayer virtu) {
		virtu.calibrateBrown();
	}

	public void calibrate(MoverLayer virtu)
	{
		calibrateBlack(virtu);
		calibrateBrown(virtu);
		calibrateWhite(virtu);
	}

	class node
	{
		color c;
		int base;

		public node(color c, int b)
		{
			this.base = b;
			this.c = c;
		}

		/**
		 * return distance in mm
		 * 
		 * @param n
		 * @return
		 */
		float distanceto(node n)
		{
			return (float) ((float) (n.base - base) * Math.PI * 56f / 360f);
		}
	};
	// Barcodes pacman
	public enum barcode	
	{
		one(0, 0, 0, 0, 0, 0, 1, 0), two(0, 0, 0, 0, 0,
				1, 0, 0), three(0, 0, 0, 0, 0, 1, 1, 0), four(0, 0, 0, 0, 1, 0, 0, 0), five(0, 
				0, 0, 0, 1, 0, 1, 0), six(0, 0, 0, 0, 1, 1, 0, 0), seven(0, 0, 0,
				0, 1, 1, 1, 0), eight(0, 0, 0, 1, 0, 0, 1, 0), nine(0, 0, 0, 1, 0, 1,
				1, 0), ten(0, 0, 0, 1, 0, 1, 1, 0), eleven(0, 0, 0, 1, 1, 0, 1, 0), twelf(0, 0, 0,
				1, 1, 1, 0, 0), thirteen(0, 0, 0, 1, 1, 1, 1, 0), fourteen(0, 0, 1, 0, 0, 0, 1, 0), fifteen(0, 
				0, 1, 0, 0, 1, 1, 0), sixteen(0, 0, 1, 0, 1, 0, 1, 0), seventeen(0, 0, 1, 0, 1, 1, 0, 0), eighteen(0, 0, 1, 0, 1, 1, 1, 0),
				nineteen(0, 0, 1, 1, 0, 0, 1, 0), twenty(0, 0, 1, 1, 0, 1, 1, 0), twentyone(0, 0, 1, 1, 1, 0, 1, 0),twentytwo(0, 0, 1, 1, 1, 1, 1, 0),
				twentythree(0, 1, 0, 0, 0, 1, 1, 0), twentyfour(0, 1, 0, 0, 1, 0, 1, 0), twentyfive(0, 1, 0, 0, 1, 1, 1, 0), twentysix(0, 1, 0, 1, 0, 1, 1, 0),
				twentyseven(0, 1, 0, 1, 1, 1, 1, 0), twentyeight(0, 1, 1, 0, 1, 1, 1, 0), onereversed(0, 1, 0, 0, 0, 0, 0, 0), tworeversed(0, 0, 1, 0, 0, 0, 0, 0),
				threereversed(0, 1, 1, 0, 0, 0, 0, 0), fourreversed(0, 0, 0, 1, 0, 0, 0, 0), fivereversed(0, 1, 0, 1, 0, 0, 0, 0), sixreversed(0, 0, 1, 1, 0, 0, 0, 0), 
				thirteenreversed(0, 1, 0, 1, 1, 0, 0, 0), seventeenreversed(0, 1, 0, 0, 0, 1, 0, 0);
				
		private final int[] code;

		private static int hammingdistance(int[] code, int[] other)
		{
			int totald = 0;
			for (int i = 0; i < other.length; i++) {
				if (code[i] == other[i])
					totald++;
			}
			return totald;
		}

		public static barcode toBarcode(int[] code)
		{
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

		barcode(int... arg)
		{
			code = arg;
		}

		public int[] getCode() {
			return code;
		}
	};

	enum color
	{
		white, black, brown;
		public String toString()
		{
			switch (this)
			{
			case white:
				return "white";
			case black:
				return "black";
			default:
				return "brown";
			}

		}

	}

	void push(node n, node[] stack)
	{
		stack[elems++] = n;
	}

	public static boolean EQual(int[] code, int[] code2)
	{
		for (int i = 0; i < code2.length; i++) {
			if (code[i] != code2[i])
				return false;
		}
		return true;
	}

	node pop(node[] stack)
	{
		return stack[elems-- - 1];
	}

	void clear(node[] stack)
	{
		for (int i = 0; i < stack.length; i++) {
			stack[i] = null;
			elems = 0;
		}
	}

	public void start(MoverLayer virtu)
	{
		this.virtu = virtu;
		Thread barCodeReader = new Thread(this);
		barCodeReader.start();
	}

	// @Override
	public void run()
	{
		color currentcolor = color.brown;
		while (true) {
			try {
				Thread.sleep(samplingtime);
			} catch (Exception e) {

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
				// lastRead = t;
				// editLastRead(t);
				clear(buffer);
				setNewBarcode(true);
				currentcolor = color.brown;
			}
		}// while true

	}

	private static boolean newBarcode = false;

	public boolean hasNewBarcode()
	{
		return BarcodeReader.newBarcode;
	}

	public void setNewBarcode(boolean newBarcode)
	{
		BarcodeReader.newBarcode = newBarcode;
	}

	@SuppressWarnings("unused")
	private void printbuffer()
	{
		for (int i = 0; i < elems; i++)
			System.out.println("N:" + i + " c:" + buffer[i].c.toString() + ":"
					+ buffer[i].base);
	}

	public static double todegrees(long cm)
	{
		return cm / Math.PI / 56 * 3600;
	}

	private node peek(node[] buffer2)
	{
		return buffer2[elems - 1];

	}

	private boolean noise()
	{
		if (elems <= 1)
			return false;
		return buffer[elems - 2].c.equals(color.brown)
				&& buffer[elems - 2].distanceto(buffer[elems - 1]) < noiseDist;
	}

	private int[] convertTocode(node[] buffer2)
	{
		@SuppressWarnings("unused")
		boolean castup = true;
		double totaldist = buffer2[elems - 1].base - buffer2[0].base;
		double basedist = totaldist / 8; 
		int[] returnvalue = new int[8];
		int filled = 0;
		for (int i = 1; i < elems; i++) {
			double dist = buffer2[i].base - buffer2[i - 1].base;
			color c = buffer2[i - 1].c;
			long UNITS = 0;
			UNITS = Math.round((dist / basedist));

			int j = filled;

			for (; j < filled + UNITS; j++) {
				switch (c)
				{
				case black:
					returnvalue[j] = 1;
					break;
				case white:
					returnvalue[j] = 0;
					break;
				}
			}
			filled = j;

		}
		return returnvalue;
	}

	public boolean iswhite(int value)
	{
		return value < getWHITE() + (getBROWN() - getWHITE()) / 2;
	}

	public boolean isblack(int value)
	{
		return value > getBLACK() - (getBLACK() - getWHITE()) / 2;
	}

	public boolean isbrown(int v)
	{
		return !isblack(v) && !iswhite(v);
	}

	public color getc(int v)
	{
		if (iswhite(v))
			return color.white;
		if (isblack(v))
			return color.black;
		return color.brown;
	}

	color getCurrentColor()
	{
		return getc(virtu.getLightSensor());
	}

	private static barcode lastRead;

	/**
	 * non destructive, will always return the barcode that was lastread.
	 * 
	 * @return
	 */
	public int getBarcode()
	{
		
		if (lastRead == null)
			return 666;
		return lastRead.ordinal();
	}

}