package tmp;

public enum Direction {

	NORTH(true),EAST(false),SOUTH(true),WEST(false);
	
	private Direction(boolean horizontal){
		
	}
	
	private boolean horizontal;
	
	public boolean isHorizontal(){
		return horizontal;
	}
}
