package board;

import java.awt.Point;

public class Panel
{
	private final boolean[] borders;
	public enum Direction
	{
		
		UP( 0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0);
		public final int x;
		public final int y;
		private Direction(int x,int y)
		{
			this.x=x;this.y=y;
		}
		public Point addTo(Point p)
		{
			return new Point(p.x+this.x,p.y+this.y);
		}
		public Direction opposite()
		{
			switch(this)
			{
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT: 
				return LEFT;
			default:
				throw new RuntimeException();
}
		}
	};
	public Panel()
	{
		borders = new boolean[4];
		for(Direction d:Direction.values())
			setBorder(d, false);
	}
	public Panel(Panel panel) {
		this.borders = new boolean[4];
		for(Direction d:Direction.values())
			this.setBorder(d, panel.getBorder(d));
	}
	public void setBorder(Direction d, Boolean b)
	{
		borders[d.ordinal()]=b;
	}
	public boolean getBorder(Direction d)
	{
		return borders[d.ordinal()];
	}
	@Override
	public Object clone()
	{
		return new Panel(this);
	}

}