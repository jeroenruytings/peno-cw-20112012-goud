package pacmansystem.ai.robot.fysicalRobot.barcode;

import java.util.Stack;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.fysicalRobot.PanelColor;

public class BarcodeFinder
{
	
	private Stack<ColorSegment> _colors;
	public BarcodeFinder(Stack<ColorNode> colors)
	{
		_colors = ColorSegment.from_trans(colors);
		
	}
	public boolean hasBarcodeAtTop()
	{
		if(_colors.peek().getColor()!=PanelColor.BROWN)
			return false;
		
		return true;
	}
	public Barcode getBarcode()
	{
		return null;
		
	}
	/**
	 * The segments passed here should be from the start of the barcode to the end of the barcode and nothing more.
	 * @param array
	 * @return
	 */
	private int[] toCode(ColorSegment[] array)
	{
		return null;
		
	}
}
