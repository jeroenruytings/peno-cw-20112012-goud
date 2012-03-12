package util.board;

import pacmansystem.ai.robot.Barcode;
import util.enums.Direction;
import util.enums.Orientation;

public class Panel
{
	private Barcode barcode;

	private final boolean[] borders;

	private Orientation barcodeOrientation;

	public Panel()
	{
		borders = new boolean[4];
		for (Orientation d : Orientation.values())
			setBorder(d, false);
	}

	public Orientation getBarcodeOrientation() {
		return barcodeOrientation;
	}

	public Panel(Panel panel)
	{
		this.borders = new boolean[4];
		for (Orientation d : Orientation.values())
			this.setBorder(d, panel.hasBorder(d));
		setBarcode(panel.getBarcode());
		setBarcodeOrientation(panel.getBarcodeOrientation());
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
		return getBarcode() != null;
	}

	public Barcode getBarcode()
	{
		return barcode;
	}

	public void setBarcode(Barcode barcode)
	{
		this.barcode = barcode;
	}
	
	public String bordersToString(){
		String result = "";
		for (Orientation d : Orientation.values()){
			if(hasBorder(d))
				result = result + " 1";
			else
				result = result + " 0";
		}		
		return result;
		
	}
	public String toString()
	{
		return bordersToString();
	}

	public void setBarcodeOrientation(Orientation currentOrientation) {
		barcodeOrientation = currentOrientation;
	}
	public boolean equal(Object o)
	{
		Panel that;
		if(o instanceof Panel)
			that = (Panel)o;
		else
			return false;
		for(Orientation d:Orientation.values())
			if(this.hasBorder(d)!=that.hasBorder(d))
				return false;
		if(this.hasBarcode())
			if(!that.hasBarcode())
				return false;
		return false;
	}

}