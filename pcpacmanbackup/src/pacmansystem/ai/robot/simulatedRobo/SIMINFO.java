package pacmansystem.ai.robot.simulatedRobo;

public class SIMINFO
{

	/**
	 * All lengths are in mm! and are seen as a horizontally placed object
	 */
	public static final float PANELWIDTH = 370;
	public static final float PANELHEIGHT = PANELWIDTH;
	public static final float WALLWIDTH = PANELWIDTH;
	public static final float WALLHEIGHT = 18;
	public static final float WHITELINEWIDH = WALLWIDTH;
	public static final float WHITELINEHEIGHT = WALLHEIGHT;
	/**
	 * As seen from a horizontal placement of a barcode.
	 */
	public static final float BARCODEWITH = PANELWIDTH;
	/**
	 * As seen from a horizontal placement of a barcode.
	 */
	public static final float BARCODEHEIGHT = 20;
	public static final int PANELZ	= 0;
	public static final int WALLZ	= 10;
	public static final int BARCODEZ = 9;
	public static final int BORDERZ = 8;
	public static final int BLACK = 500;
	public static final int BROWN = 250;
	public static final int WHITE = 100;
	public static final int LIGHTSENSORPOSITION = -20;
	public static final int LIGHTSENSORDISTANCE = 30;
}
