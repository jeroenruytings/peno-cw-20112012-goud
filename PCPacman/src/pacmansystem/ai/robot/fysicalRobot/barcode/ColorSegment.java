package pacmansystem.ai.robot.fysicalRobot.barcode;

import java.util.Stack;


import pacmansystem.ai.robot.fysicalRobot.PanelColor;

public class ColorSegment
{
	private PanelColor _color;
	private int _length;

	public ColorSegment(ColorNode one,ColorNode two)
	{
		_color = one.get_color();
		_length = two.get_dist()-one.get_dist();
	}
	public PanelColor getColor(){return _color;}
	public int 		  getLength(){return _length;}
	public static Stack<ColorSegment> from_trans(Stack<ColorNode> nodes)
	{
		if(nodes.size()<2)
			return new Stack<ColorSegment>();

		Stack<ColorSegment> rv = new Stack<ColorSegment>();
		
		if(nodes.size()==2)
		{
			ColorNode n = nodes.pop();
			rv.push(new ColorSegment(nodes.peek(),n));
			return rv;
		}
		ColorNode n = nodes.pop();
		ColorSegment segment = new ColorSegment(nodes.peek(), n);
		rv = from_trans(nodes);
		rv.push(segment);
		return rv;
	}
}
