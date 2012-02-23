package board;

import java.awt.Point;

import panel.Barcode;

public class Panel
{
	private Barcode barcode;
	
	private final boolean[] borders;
	public enum Orientation
	{
		
		NORTH(0,-1),SOUTH(0,1),WEST(-1,0),EAST(1,0);
		
		public final int x;
		public final int y;
		
		private Orientation(int x,int y)
		{
			this.x=x;
			this.y=y;
		}
		
		public Point addTo(Point p)
		{
			return new Point(p.x+this.x,p.y+this.y);
		}
		
		public Orientation opposite()
		{
			switch(this)
			{
				case NORTH:
					return SOUTH;
				case SOUTH:
					return NORTH;
				case WEST:
					return EAST;
				case EAST: 
					return WEST;
				default:
					throw new RuntimeException();
			}
		}
	};
	
	public Panel()
	{
		borders = new boolean[4];
		for(Orientation d:Orientation.values())
			setBorder(d, false);
	}
	
	public Panel(Panel panel) {
		this.borders = new boolean[4];
		for(Orientation d:Orientation.values())
			this.setBorder(d, panel.getBorder(d));
	}
	
	public void setBorder(Orientation d, Boolean b)
	{
		borders[d.ordinal()]=b;
	}
	
	public boolean getBorder(Orientation d)
	{
		return borders[d.ordinal()];
	}
	
	@Override
	public Object clone()
	{
		return new Panel(this);
	}

}