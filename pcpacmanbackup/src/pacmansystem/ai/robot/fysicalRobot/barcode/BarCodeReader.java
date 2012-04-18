package pacmansystem.ai.robot.fysicalRobot.barcode;

import java.util.Map;

import pacmansystem.ai.robot.Barcode;

public class BarCodeReader
{
	private ColorTransitionStack _stack;
	private Map<int[], Barcode> _codes;



	public BarCodeReader(ColorTransitionStack stack,Map<int[], Barcode>codes)
	{
		_stack = stack;
		_codes = codes;
	}
	
	public Barcode searchForCode()
	{
		
		BarcodeFinder finder = new BarcodeFinder(_stack.getColors(), 8);
		
		return closestHamming(finder.getCode());

	}

	private Barcode closestHamming(int[] thi)
	{
		String print = "";
			for (int i : thi)
				print += i;
		System.out.println("gelezen voor closest hamming " + print);
		int max = -1;
		Barcode best = null;
		for (int[] key : _codes.keySet()) {
			if (thi.length == key.length) {
				if (hamming(thi, key) > max) {
					max = hamming(thi, key);
					best = _codes.get(key);
				}
			}
		}
		return best;
	}

	int hamming(int[] a, int[] b)
	{
		int rv = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] == b[i])
				rv++;
		return rv;
	}

}
