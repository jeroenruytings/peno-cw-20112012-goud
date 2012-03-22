package data.board;

import data.enums.Orientation;
import pacmansystem.ai.robot.Barcode;

public class Panel
{
	private Barcode barcode;

	private final WallState[] borders;

	private Orientation barcodeOrientation;

	public Panel()
	{
		borders = new WallState[4];
		for (Orientation d : Orientation.values())
			setBorder(d, WallState.UNKNOWN);
	}

	public Orientation getBarcodeOrientation() {
		return barcodeOrientation;
	}

	public Panel(Panel panel)
	{
		this.borders = new WallState[4];
		for (Orientation d : Orientation.values()){
			this.setBorder(d, panel.getWallState(d));
		}
		setBarcode(panel.getBarcode());
		setBarcodeOrientation(panel.getBarcodeOrientation());
	}

	public void setBorder(Orientation d, WallState state)
	{
		borders[d.ordinal()] = state;
	}
	
	public WallState getWallState(Orientation d){
		return borders[d.ordinal()];
	}

	public boolean hasBorder(Orientation d)
	{
		return borders[d.ordinal()]==WallState.WALL;
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
	public boolean equals(Object o)
	{
		Panel panel;
		if(o instanceof Panel)
			panel = (Panel)o;
		else
			return false;
		for(Orientation d:Orientation.values())
			if(this.hasBorder(d)!=panel.hasBorder(d))
				return false;
		if(this.hasBarcode() != panel.hasBarcode())
				return false;
		return true;
	}
	
	/**
	 * Verander de volgorde van de elementen niet!
	 * Gekoppeld aan CommandDiscover!
	 */
	public enum WallState{
		PASSAGE,WALL,UNKNOWN;
	}
}