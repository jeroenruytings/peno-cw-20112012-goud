package pacmansystem.ai.robot.fysicalRobot.barcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.fysicalRobot.PanelColor;

public class BarcodeFinder
{
	int _elems = 3;
	private Stack<ColorSegment> _colors;
	public BarcodeFinder(Stack<ColorNode> colors,int elems)
	{
		_colors = ColorSegment.from_trans(colors);
		_elems=elems;
	}
	public boolean hasBarcodeAtTop()
	{
		if(_colors.peek().getColor()!=PanelColor.BROWN)
			return false;
		ColorSegment[] toSegment = topSegment();
		
		return false;
	}
	private ColorSegment[] topSegment()
	{
		Stack<ColorSegment> colors = (Stack<ColorSegment>) _colors.clone();
		Stack<ColorSegment> rv = new Stack<ColorSegment>();
		if(colors.peek().getColor()==PanelColor.BROWN)
		{
			colors.pop();
			
		}
		ColorSegment current =colors.pop();
		while(!current.getColor().equals(PanelColor.BROWN))
		{
			rv.push(current);
			current = colors.pop();
		}
		ColorSegment[] r = new ColorSegment[rv.size()];
		int i = 0;
		while(!rv.isEmpty())
			r[i++]=rv.pop();
		return r;
	}
	public Barcode getBarcode()
	{
		
		
		 return null;
	}
	public int[] getCode()
	{
		 return toCode(this.topSegment());
			
	}
	/**
	 * The segments passed here should be from the start of the barcode to the end of the barcode and nothing more.
	 * @param array
	 * @return
	 */
	private int[] toCode(ColorSegment[] array)
	{
		
		int[] rv = new int[_elems];
		int L = distance(array);
		float offset = ((float)L)/_elems;
		for(int i = 0 ; i < rv.length;i++)
		{
			switch(rulingColor(array,offset*i,offset*(i+1)))
			{
			case BLACK:
				rv[i]=0;
				break;
			case WHITE:
				rv[i]=1;
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
		for(int i  = 0 ; i < array.length;i++)
		{
			if(dist+array[i].getLength()<f){dist+=array[i].getLength();
				continue;}
			if(dist>g)
				break;
			float addedValue = Math.min(dist+array[i].getLength(),g)-Math.max(f,dist);
			addTo(map,array[i].getColor(),addedValue);
			dist+=array[i].getLength();
		}
		PanelColor max = PanelColor.BROWN;
		float m = 0;
		for(PanelColor c:PanelColor.values())
			if(get(map,c)>m)
				{
					m=get(map,c);
					max=c;
				}
		
		return max;
	}
	private float get(Map<PanelColor, Float> map, PanelColor c)
	{
		if(map.containsKey(c))
			return map.get(c);
		return 0;
	}
	private void addTo(Map<PanelColor, Float> map, PanelColor color,
			float addedValue)
	{
		if(map.containsKey(color))
			map.put(color, map.get(color)+addedValue);
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
		for(ColorSegment s: array)
			rv+=s.getLength();
		
		return rv;
	}
}
