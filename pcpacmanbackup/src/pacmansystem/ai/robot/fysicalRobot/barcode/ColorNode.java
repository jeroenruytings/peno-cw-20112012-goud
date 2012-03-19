package pacmansystem.ai.robot.fysicalRobot.barcode;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;

public class ColorNode
{
	private PanelColor _color;

	public ColorNode(PanelColor c,int dist)
	{
		_color = c;
		_dist = dist;
	}
	public PanelColor get_color()
	{
		return _color;
	}

	public int get_dist()
	{
		return _dist;
	}

	private int _dist;
	public String toString()
	{
		return _color+": "+_dist;
	}
	@Override
	public boolean equals(Object o)
	{
		if(! (o instanceof ColorNode))
			return false;
		ColorNode that = (ColorNode)o;
		return this.get_color() == that.get_color()&&this.get_dist()==that.get_dist();
	}
}
