package pacmansystem.ai.robot.fysicalRobot.barcode;

import static org.junit.Assert.*;

import org.junit.Test;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;

public class _ColorTransitionStack
{
	public static void standardCalibrate(ColorTransitionStack stack)
	{
		stack.calibrate(PanelColor.BLACK, 100);
		stack.calibrate(PanelColor.BROWN, 50);
		stack.calibrate(PanelColor.WHITE, 0);
	}
	@Test
	public void creationTest()
	{
		ColorTransitionStack stack = new ColorTransitionStack(null);
		standardCalibrate(stack);
		stack.pushColor(0, 10);
		stack.pushColor(26, 21);
		
	}
	@Test
	public void addOffsetFail()
	{
		ColorTransitionStack stack = new ColorTransitionStack(null);
		standardCalibrate(stack);
		stack.pushColor(0, 11);
		stack.pushColor(26, 19);
		assertTrue(	stack.getColors().peek().get_color().equals(PanelColor.WHITE));
	}
	@Test
	public void addColorFail()
	{
		ColorTransitionStack stack = new ColorTransitionStack(null);
		standardCalibrate(stack);
		stack.pushColor(0, 11);
		stack.pushColor(0, 150);
		assertTrue(stack.getColors().size()==2);
	}
}
