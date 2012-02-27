package board;

import mainController.Orientation;

import panel.Barcode;

public class Panel
{
	private Barcode barcode;
	
	private final boolean[] borders;
	
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