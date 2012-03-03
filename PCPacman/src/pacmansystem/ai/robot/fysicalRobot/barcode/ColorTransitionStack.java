package pacmansystem.ai.robot.fysicalRobot.barcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;

public class ColorTransitionStack
{

	private Map<PanelColor, Integer> _colorCalibration;
	private Stack<ColorNode> _nodes = new Stack<>();
	private static final int FILTER_OFFSET = 10;

	public ColorTransitionStack(MoverLayer layer)
	{
		_colorCalibration = new HashMap<PanelColor, Integer>();
		_nodes.add(new ColorNode(PanelColor.BROWN, 0));
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

	private boolean sufficientlyCalibrated()
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
		if (distance(current, nodes.peek()) < FILTER_OFFSET) {
			return cleanHeap(nodes);
		}else
		{
			
		}
		nodes.push(current);
		return nodes;

	}

	private int distance(ColorNode one, ColorNode two)
	{
		return Math.abs(one.get_dist() - two.get_dist());
	}

	public Integer getColor(PanelColor key)
	{
		return _colorCalibration.get(key);
	}

}
