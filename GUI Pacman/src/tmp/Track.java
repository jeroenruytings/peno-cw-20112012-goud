package tmp;

public class Track {
	
	private Panel[][] track;
	
	public Track(Panel[][] track){
		this.track = track;
	}
	
	/**
	 * @return The track of this {@link Track} object, in a 2-dimentional array of {@link Panel} objects.
	 */
	public Panel[][] getTrack(){
		return track;
	}
	
	public void addPanel(Panel panel, Coordinate relativePosition){
		if (track == null){
			track = new Panel[relativePosition.getX() + 1][relativePosition.getY()+1];
			track[relativePosition.getX() -1][relativePosition.getY() - 1] = panel;
		}
		else if (track[relativePosition.getX() -1][relativePosition.getY() - 1] == null) {
			//TODO check the surroundings
		}
		
	}
	
	public int getHeight(){
		return 0;
	}
	
	public int getWidth(){
		return 0;
	}
	
	
	public Panel getPanel(Coordinate position){
		if((position.getX() < 0) || (position.getY() < 0) || (position.getX() >= track[0].length) || (position.getY() >= track.length)){
			return null;
		}
		else
			return track[position.getX()][position.getY()];
	}
	
	
	

}
