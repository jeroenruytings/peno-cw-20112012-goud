package pacmansystem.ai.robot.fysicalRobot.barcode;

import pacmansystem.ai.robot.Barcode;

public class BarCodeReader
{
	private ColorTransitionStack _stack;

	public BarCodeReader(ColorTransitionStack stack)
	{
		_stack=stack;
	}
	
	public Barcode searchForCode()
	{
		BarcodeFinder finder = new BarcodeFinder(_stack.getColors());
		
		return  null;
	}
	
}
