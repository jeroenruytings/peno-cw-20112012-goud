package gui.tmp;

public enum Direction {

	NORTH(true),EAST(false),SOUTH(true),WEST(false);
	
	private Direction(boolean horizontal){
		this.horizontal = horizontal;
	}
	
	private boolean horizontal;
	
	public boolean isHorizontal(){
		return horizontal;
	}

	public boolean neighbor(Direction tmp) {
		if (tmp == null)
			return false;
		return (this.isHorizontal() != tmp.isHorizontal());
	}
}
