package pacmansystem.board;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.board.enums.Orientation;

public class Panel
{
	private Barcode barcode;

	private final boolean[] borders;

	public Panel()
	{
		borders = new boolean[4];
		for (Orientation d : Orientation.values())
			setBorder(d, false);
	}

	public Panel(Panel panel)
	{
		this.borders = new boolean[4];
		for (Orientation d : Orientation.values())
			this.setBorder(d, panel.hasBorder(d));
	}

	public void setBorder(Orientation d, Boolean b)
	{
		borders[d.ordinal()] = b;
	}

	public boolean hasBorder(Orientation d)
	{
		return borders[d.ordinal()];
	}

	@Override
	public Object clone()
	{
		return new Panel(this);
	}

	public boolean hasBarcode()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public Barcode getBarcode()
	{
		return barcode;
	}

	public void setBarcode(int barcode)
	{
		//TODO: CREATE BARCODE CLASS!!!!

	}

}