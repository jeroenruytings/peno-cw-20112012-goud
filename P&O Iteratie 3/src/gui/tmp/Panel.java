package gui.tmp;

public class Panel {
	
	
	private boolean n;
	private boolean s;
	private boolean e;
	private boolean w;
	
	private Coordinate globalPosition;
	
	
	public Panel(Coordinate position, boolean n,boolean e,boolean s,boolean w){
		this.n = n;
		this.s = s;
		this.w = w;
		this.e = e;
		this.globalPosition = position;
	}

	public boolean hasBorder(Direction d){
		switch (d){
		case NORTH:
			return n;
		case EAST:
			return e;
		case SOUTH:
			return s;
		case WEST:
			return w;
		default:
			return false;

		}
	}

	/**
	 * @return the globalPosition
	 */
	public Coordinate getGlobalPosition() {
		return globalPosition;
	}
	
	

}
