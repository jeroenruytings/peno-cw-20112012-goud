package pacmansystem.ai.robot.fysicalRobot.barcode;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pacmansystem.ai.robot.Barcode;

public class _BarcodeReaderTest0
{
	
	ColorTransitionStack stack;
	private Map<int[], Barcode> barcodes;
	@Before
	public void before()
	{
		stack = new ColorTransitionStack(null);
	_ColorTransitionStack.standardCalibrate(stack);
	barcodes = new HashMap<int[], Barcode>();
	int[] r = {0,0,0,1,1,1,1};
	barcodes.put(r, Barcode.one);
	}
	@Test
	public void ezpz()
	{
		stack.pushColor(100, 50);
		stack.pushColor(0, 99);
		stack.pushColor(50, 150);
		
		BarCodeReader reader = new BarCodeReader(stack,barcodes);
		assertTrue(reader.searchForCode().equals(Barcode.one));
	
	}
	private void prettyPrint(int[] v)
	{
		System.out.println("[");
		for(int i : v)
			System.out.println(i);
		System.out.println("]");
	}
}
