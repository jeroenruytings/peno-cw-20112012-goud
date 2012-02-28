package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import mainController.Orientation;


/**
 * 
 * @author Dieter
 * 
 *         This class assures that 2 boards that are next to eachother(
 *         according to our coordinate system) have no conflicting borders:
 * 
 */
public class Board {
	private Map<Point, Panel> panels;
	private int rows;
	private int columns;

	public Board(int rows, int columns) {
		panels = new HashMap<Point, Panel>();
		this.rows = rows;
		this.columns = columns;
	}
	
	/**
	 * This constructor does not specify the dimensions of the board.
	 * The dimensions are the maxX and maxY coordinates.
	 */
	public Board(){
		this(0,0);
	}
	
	public int getRows() {
		if (rows > maxY())
			return rows;
		return maxY();
	}
	
	public int getColumns() {
		if (columns > maxX())
			return columns;
		return maxX();
	}

	public Board(Board board) {
		this.panels = new HashMap<Point, Panel>();
		for (Point p : board.getFilledPoints())
			this.add(board.getPanelAt(p), p);

	}

	/**
	 * Adds a panel and if it is conflicting with the already added panels it
	 * throws an exception
	 * 
	 */
	public void add(Panel panel, Point p) {
		if (conflicting(p, panel))
			throw new IllegalArgumentException();
		panels.put(p, panel);
	}

	/**
	 * If a border panel has a different
	 * 
	 * @param p
	 * @param panel
	 * @return
	 */
	private boolean conflicting(Point p, Panel panel) {
		for (Orientation d : Orientation.values())
			if (hasPanelAt(d.addTo(p))) {
				if (panel.getBorder(d) != this.getPanelAt(d.addTo(p))
						.getBorder(d.opposite()))
					return true;
			}
		return false;
	}

	public void addForced(Panel panel, Point point) {
		this.panels.put(point, panel);
		for (Orientation d : Orientation.values())
			if (hasPanelAt(d.addTo(point)))
				panels.get(point).setBorder(d.opposite(), panel.getBorder(d));
	}

	public boolean hasPanelAt(Point p) {
		return panels.containsKey(p);
	}

	/**
	 * ReturNS null if there is no panel at this location
	 * 
	 */
	public Panel getPanelAt(Point p) {
		if (!hasPanelAt(p))
			return null;
		return new Panel(panels.get(p));
	}
	
	public Map<Point,Panel> getPanels(){
		return panels;
	}

	public void clear() {

	}

	public Iterable<Point> getFilledPoints() {
		return new ArrayList<Point>(panels.keySet());
	}
	public Collection<Point> getSurrounding(Point p)
	{
		ArrayList<Point> rv = new ArrayList<Point>();
		for(Orientation d:Orientation.values()){
			Point newPoint = d.addTo(p);
			if (!outOfCoords(newPoint))
				rv.add(newPoint);
		}
	return rv;
	}
	
	private boolean outOfCoords(Point point) {
		if (point.getX() > columns || point.getX() < 0)
			return true;
		if (point.getY() > rows || point.getY() < 0)
			return true;
		return false;
	}
	
	@Override
	public Object clone() {
		return new Board(this);
	}

	public int maxX() {
		int max = 0;
		for (Point p : panels.keySet())
			if (p.x > max)
				max = p.x;
		return max;

	}

	public int maxY() {
		int max = 0;
		for (Point p : panels.keySet())
			if (p.y > max)
				max = p.y;
		return max;
	}
	
	/**
	 * 
	 * @param one
	 * @param orientation
	 * @return	true als er een muur tussen ligt
	 * @return 	false als er geen muur tussen ligt, of het niet weet
	 */
	public boolean wallBetween(Point one, Orientation orientation) {
		
		if(one==null)
			throw new NullPointerException();
		if(getPanelAt(one)==null)
			if(getPanelAt(orientation.addTo(one))==null)
				return false;
			else
				return getPanelAt(orientation.addTo(one)).getBorder(orientation.opposite());
		return getPanelAt(one).getBorder(orientation);
		
	}
	
	/**
	 * Geeft aan of er een muur ligt tussen de twee punten
	 * @pre	de twee punten moeten VLAK naast elkaar liggen
	 * @param one
	 * @param two
	 * @return	true als er een muur tussen ligt
	 * @return 	false als er geen muur tussen ligt, of het niet weet
	 */
	public boolean wallBetween(Point one, Point two) {
		if(one==null)
			throw new NullPointerException();
		if(two==null)
			throw new NullPointerException();
		for (Orientation orientation : Orientation.values()){
			Point point = orientation.addTo(one);
			if (point.getX() == two.getX() && point.getY() == two.getY())
				return wallBetween(one,orientation);
		}
		return false; //<-- DIT MAG NOOIT GEBEUREN @pre
	}
	
	/**
	 * Geeft terug hoeveel punten rond dit punt gekend zijn. (bestaan al)
	 * @param point
	 * @return
	 */
	public int nbOfUnknowns(Point point){
		int nbUnknown = 0;
		Collection<Point> points = getSurrounding(point);
		for(Point current: getSurrounding(point)){
			if (panels.containsKey(current))
				continue;
			if (wallBetween(point,current))
				continue;
			nbUnknown++;
			
		}
		return nbUnknown;
	}
	
}