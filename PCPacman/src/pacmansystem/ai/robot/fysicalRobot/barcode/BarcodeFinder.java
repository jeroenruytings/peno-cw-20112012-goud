package pacmansystem.ai.robot.fysicalRobot.barcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;

public class BarcodeFinder
{
	int _elems = 3;
	private Stack<ColorSegment> _colors;

	public BarcodeFinder(Stack<ColorNode> colors, int elems)
	{
		_colors = ColorSegment.from_trans(colors);
		_elems = elems;
	}

	public boolean hasBarcodeAtTop()
	{
		if (_colors.peek().getColor() != PanelColor.BROWN)
			return false;
		if(getCode()==null)
			return false;
		return true;
	}

	private ColorSegment[] topSegment()
	{
		Stack<ColorSegment> colors = (Stack<ColorSegment>) _colors.clone();
		Stack<ColorSegment> rv = new Stack<ColorSegment>();
		if (colors.peek().getColor() == PanelColor.BROWN) {
			colors.pop();
		}
		if(colors.isEmpty())
			return new ColorSegment[0];
		ColorSegment current = colors.pop();
		while (!endOfBarcode(current)) {
			rv.push(current);
			current = colors.pop();
		}
		ColorSegment[] r = new ColorSegment[rv.size()];
		int i = 0;
		while (!rv.isEmpty())
			r[i++] = rv.pop();
		return r;
	}

	private boolean endOfBarcode(ColorSegment current)
	{
		return current.getColor().equals(PanelColor.BROWN);
	}

	public int[] getCode()
	{
		ColorSegment[] segments = this.topSegment();
		//segments = filter(segments);
		if(segments.length==0)
			return new int[0];
		return toCode(segments);

	}

	private ColorSegment[] filter(ColorSegment[] segments)
	{
		// right now filter the black bars @ start & end
		int e = _elems + 2;
		float total = distance(segments);
		float offset = total / e;

		if (segments[0].getColor() == PanelColor.BLACK) {
			segments[0] = (new ColorSegment(new ColorNode(PanelColor.BLACK, 0),
					new ColorNode(PanelColor.BLACK, (int) Math.max(
							segments[0].getLength() - offset, 0))));
		}
		if (segments[segments.length - 1].getColor() == PanelColor.BLACK) {
			segments[segments.length - 1] = (new ColorSegment(new ColorNode(
					PanelColor.BLACK, 0), new ColorNode(PanelColor.BLACK,
					(int) Math.max(segments[segments.length - 1].getLength()
							- offset, 0))));
		}
		return segments;
	}

	/**
	 * The segments passed here should be from the start of the barcode to the
	 * end of the barcode and nothing more.
	 * 
	 * @param array
	 * @return
	 */
	private int[] toCode(ColorSegment[] array)
	{

		int[] rv = new int[_elems];
		int L = distance(array);
		float offset = ((float) L) / _elems;
		for (int i = 0; i < rv.length; i++) {
			switch (rulingColor(array, offset * i, offset * (i + 1)))
			{
			case BLACK:
				rv[i] = 0;
				break;
			case WHITE:
				rv[i] = 1;
				break;
			default:
				break;
			}
		}
		return rv;

	}

	private PanelColor rulingColor(ColorSegment[] array, float f, float g)
	{

		int dist = 0;
		int total = distance(array);
		Map<PanelColor, Float> map = new HashMap<PanelColor, Float>();
		for (int i = 0; i < array.length; i++) {
			if (dist + array[i].getLength() < f) {
				dist += array[i].getLength();
				continue;
			}
			if (dist > g)
				break;
			float addedValue = Math.min(dist + array[i].getLength(), g)
					- Math.max(f, dist);
			addTo(map, array[i].getColor(), addedValue);
			dist += array[i].getLength();
		}
		PanelColor max = PanelColor.BROWN;
		float m = 0;
		for (PanelColor c : PanelColor.values())
			if (get(map, c) > m) {
				m = get(map, c);
				max = c;
			}

		return max;
	}

	private float get(Map<PanelColor, Float> map, PanelColor c)
	{
		if (map.containsKey(c))
			return map.get(c);
		return 0;
	}

	private void addTo(Map<PanelColor, Float> map, PanelColor color,
			float addedValue)
	{
		if (map.containsKey(color))
			map.put(color, map.get(color) + addedValue);
		else
			map.put(color, addedValue);

	}

	private int max(int l1, int dist)
	{
		return Math.max(l1, dist);
	}

	private int distance(ColorSegment[] array)
	{
		int rv = 0;
		for (ColorSegment s : array)
			rv += s.getLength();

		return rv;
	}
}
