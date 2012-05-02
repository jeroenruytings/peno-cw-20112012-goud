package pacmansystem.ai.robot;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.ai.robot.fysicalRobot.barcode.BarcodeFinder;
import pacmansystem.ai.robot.fysicalRobot.barcode.ColorTransitionStack;

public class BarcodesTest
{

	private ColorTransitionStack stack;
	private int black = 500;
	private int brown = 400;
	private int white = 100;

	@Before
	public void init()
	{
		stack = new ColorTransitionStack();
		stack.calibrate(PanelColor.BLACK, 500);
		stack.calibrate(PanelColor.WHITE, 100);
		stack.calibrate(PanelColor.BROWN, brown);

	}

	@Test
	public void test0()
	{
		stack.clear();
		stack.pushColor(brown, 12);
		stack.pushColor(black, 50);
		stack.pushColor(white, 130);
		stack.pushColor(black, 150);
		stack.pushColor(brown, 210);
		BarcodeFinder f = new BarcodeFinder(stack.getColors(),8);
		print(f.getCode());
	}
	public void print(int...i)
	{
		for(int j : i)
			System.out.print(j);
	}
}
