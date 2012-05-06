package pacmansystem.ai.robot.fysicalRobot.barcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;

public class ColorTransitionStack
{

	private Map<PanelColor, Integer> _colorCalibration;
	private Stack<ColorNode> _nodes = new Stack<ColorNode>();
	private static final int FILTER_OFFSET = 0;

	@Deprecated
	public ColorTransitionStack(MoverLayer layer)
	{
		_colorCalibration = new HashMap<PanelColor, Integer>();
		_nodes.add(new ColorNode(PanelColor.BROWN, 0));
	}

	public ColorTransitionStack()
	{
		this(null);
	}

	@SuppressWarnings("unchecked")
	public Stack<ColorNode> getColors()
	{
		return (Stack<ColorNode>) _nodes.clone();

	}

	/**
	 * 
	 * @param lightValue
	 * @param distance
	 */
	public void pushColor(int lightValue, int distance)
	{
		addColorNode(getColor(lightValue), distance);

	}

	public void calibrate(PanelColor color, int lightValue)
	{
		_colorCalibration.put(color, lightValue);
	}

	public PanelColor getColor(int lightValue)
	{
		if (isBlack(lightValue))
			return PanelColor.BLACK;
		if (isWhite(lightValue))
			return PanelColor.WHITE;
		return PanelColor.BROWN;
	}

	private void addColorNode(PanelColor color, int distance)
	{
		if (!_nodes.isEmpty() && distance < _nodes.peek().get_dist())
			return;
		_nodes.push(new ColorNode(color, distance));
		cleanHeap();
	}

	private boolean isBlack(int value)
	{
		return (value > (_colorCalibration.get(PanelColor.BLACK) + _colorCalibration
				.get(PanelColor.BROWN)) / 2);
	}

	private boolean isWhite(int value)
	{
		return (value < (_colorCalibration.get(PanelColor.WHITE) + _colorCalibration
				.get(PanelColor.BROWN)) / 2);
	}

	public boolean sufficientlyCalibrated()
	{
		for (PanelColor c : PanelColor.values())
			if (!_colorCalibration.containsKey(c))
				return false;
		return true;
	}

	private void cleanHeap()
	{
		_nodes = cleanHeap(_nodes);
	}

	private Stack<ColorNode> cleanHeap(Stack<ColorNode> nodes)
	{
		if (nodes.size() <= 2)
			return nodes;
		ColorNode current = nodes.pop();

		if (current.get_color().equals(nodes.peek().get_color())) {
			return cleanHeap(nodes);
		}
//		if (distance(current, nodes.peek()) < FILTER_OFFSET) {
//			nodes.pop();
//			nodes.push(current);
//			return cleanHeap(nodes);
//		}
		nodes.push(current);
		return nodes;

	}

	private int distance(ColorNode one, ColorNode two)
	{
		int distance = one.get_dist() - two.get_dist();
		return distance;
	}

	public Integer getColor(PanelColor key)
	{
		return _colorCalibration.get(key);
	}

	public void clear()
	{
		_nodes = new Stack<ColorNode>();
		_nodes.add(new ColorNode(PanelColor.BROWN, 0));
	}
}
