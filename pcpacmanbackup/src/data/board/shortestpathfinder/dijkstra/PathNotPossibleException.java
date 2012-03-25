package data.board.shortestpathfinder.dijkstra;

public class PathNotPossibleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public PathNotPossibleException(String message){
		super(message);
	}
	public PathNotPossibleException(){
		this("");
	}
}
