package data.board;

import java.util.HashMap;
import java.util.Map;

import pacmansystem.ai.robot.Barcode;
import data.enums.Orientation;

public class Panel
{
	private Barcode barcode;

	private final Map<Orientation, WallState> borders;

	private Orientation barcodeOrientation;

	public Panel()
	{
		borders = new HashMap<Orientation, Panel.WallState>();
		for (Orientation d : Orientation.values())
			setBorder(d, WallState.UNKNOWN);
	}

	public Orientation getBarcodeOrientation() {
		return barcodeOrientation;
	}

	public Panel(Panel panel)
	{
		this.borders = new HashMap<Orientation, WallState>();
		for (Orientation d : Orientation.values()){
			this.setBorder(d, panel.getWallState(d));
		}
		setBarcode(panel.getBarcode(), panel.getBarcodeOrientation());
	}
	
	public Panel(int n, int e, int s, int w){
		this();
		this.setBorder(Orientation.NORTH, WallState.getWallState(n));
		this.setBorder(Orientation.EAST, WallState.getWallState(e));
		this.setBorder(Orientation.SOUTH, WallState.getWallState(s));
		this.setBorder(Orientation.WEST, WallState.getWallState(w));
		
	}

	public void setBorder(Orientation d, WallState state)
	{
		borders.put(d,state);
	
	}
	
	public WallState getWallState(Orientation d){
		return borders.get(d);
	}

	public boolean hasBorder(Orientation d)
	{
		return borders.get(d)==WallState.WALL;
	}
	
	public boolean hasPassage(Orientation d) {
		return borders.get(d) == WallState.PASSAGE;
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

	public void setBarcode(Barcode barcode, Orientation orientation)
	{
		this.barcode = barcode;
		setBarcodeOrientation(orientation);
	}
	
	public String bordersToString(){
		String result = "";
		for (Orientation d : Orientation.values()){
			switch(getWallState(d))
			{
			case UNKNOWN:
				result+=" 2";
				break;
			case PASSAGE:
				result+=" 0";
				break;
			case WALL:
				result+=" 1";
				break;
			}
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
		for(Orientation d:Orientation.values()) {
			if (this.getWallState(d)==WallState.UNKNOWN || panel.getWallState(d) == WallState.UNKNOWN)
				continue;
			if(this.getWallState(d)!=panel.getWallState(d))
				return false;
			if(this.hasBarcode() != panel.hasBarcode())
				return false;
		}
		return true;
	}
	
	/**
	 * Verander de volgorde van de elementen niet!
	 * Gekoppeld aan CommandDiscover!
	 */
	public enum WallState{
		PASSAGE(0),WALL(1),UNKNOWN(2);
		
		private int value;
		private WallState(int value){
			this.value = value;
		}
		
		public int toInt(){
			return value;
		}
		
		public static WallState getWallState(int i){
			switch (i){
			case 0:
				return PASSAGE;
			case 1:
				return WALL;
			case 2:
				return UNKNOWN;
			default:
				throw new IllegalArgumentException("Een verkeerde wallstate is doorgegeven.");
					
			}
		}
	}
	
	/**
	 * @return A panel equal to this one but without the barcode.
	 */
	public Panel noBarcode()
	{
		Panel result = new Panel();
		for(Orientation dir:Orientation.values())
			result.setBorder(dir, this.getWallState(dir));
		return result;
	}
}