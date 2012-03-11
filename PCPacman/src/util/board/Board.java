package util.board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import pacmansystem.ai.robot.Barcode;
import util.enums.Orientation;

/**
 * 
 * @author Dieter
 * 
 *         This class assures that 2 boards that are next to eachother(
 *         according to our coordinate system) have no conflicting borders:
 * 
 */
public class Board  
{
	private ConcurrentHashMap<Point, Panel> panels;
	private int rows;
	private int columns;

	public Board(int rows, int columns)
	{
		panels = new ConcurrentHashMap<Point, Panel>();
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * This constructor does not specify the dimensions of the board. The
	 * dimensions are the maxX and maxY coordinates.
	 */
	public Board()
	{
		this(0, 0);
	}

	public int getRows()
	{
		if (rows > maxY())
			return rows;
		return maxY();
	}

	public int getColumns()
	{
		if (columns > maxX())
			return columns;
		return maxX();
	}

	public Board(Board board)
	{
		this.panels = new ConcurrentHashMap<Point, Panel>();
		for (Point p : board.getFilledPoints())
			this.add(board.getPanelAt(p), p);

	}

	/**
	 * Adds a panel and if it is conflicting with the already added panels it
	 * throws an exception
	 * 
	 */
	public void add(Panel panel, Point p)
	{
//		if (conflicting(p, panel))
//			throw new IllegalArgumentException("Paneel fout: " + p);
		panels.put(p, panel);
		calcDimensions();
	}

	/**
	 * If a border panel has a different
	 * 
	 * @param p
	 * @param panel
	 * @return
	 */
	private boolean conflicting(Point p, Panel panel)
	{
		for (Orientation d : Orientation.values())
			if (hasPanelAt(d.addTo(p))) {
				if (panel.hasBorder(d) != this.getPanelAt(d.addTo(p))
						.hasBorder(d.opposite()))
					return true;
			}
		return false;
	}

	public void addForced(Panel panel, Point point)
	{
		this.panels.remove(point);
		this.panels.put(point, panel);
		for (Orientation d : Orientation.values()){
			if (hasPanelAt(d.addTo(point))){
				panels.get(d.addTo(point)).setBorder(d.opposite(),
						panel.hasBorder(d));
			}
		}
		System.out.println("w");
		calcDimensions();
	}

	public boolean hasPanelAt(Point p)
	{
		return panels.containsKey(p);
	}

	/**
	 * ReturNS null if there is no panel at this location
	 * 
	 */
	public Panel getPanelAt(Point p)
	{
		if (!hasPanelAt(p))
			return null;
		return new Panel(getPanels().get(p));
	}
	
	public void setBarcode(Point coordinate, Barcode barcode, Orientation orient){
		Panel p = panels.get(coordinate); 
		p.setBarcode(barcode);
		p.setBarcodeOrientation(orient);
	}

	public Map<Point, Panel> getPanels()
	{
			return new ConcurrentHashMap<Point,Panel>(this.panels);
	}

	public void clear()
	{
		this.panels = new ConcurrentHashMap<Point,Panel>();
		
	}
	/**
	 * Return all the points that have a panel object set
	 * @return
	 */
	public  Iterable<Point> getFilledPoints()
	{
		CopyOnWriteArrayList<Point> rv ;
			rv = new CopyOnWriteArrayList<Point>(getPanels().keySet());
		return rv;
	}

	public Collection<Point> getSurrounding(Point p)
	{
		ArrayList<Point> rv = new ArrayList<Point>();
		for (Orientation d : Orientation.values()) {
			Point newPoint = d.addTo(p);
			//if (!outOfCoords(newPoint))
				rv.add(newPoint);
		}
		return rv;
	}

	private boolean outOfCoords(Point point)
	{
		if (point.getX() >= (maxX() - minX()))
			return true;
		if (point.getY() >= (maxY() - minY()))
			return true;
		return false;
	}

	@Override
	public Object clone()
	{
		return new Board(this);
	}
	
	private synchronized void calcDimensions(){
		calcMaxX();
		calcMaxY();
		calcMinX();
		calcMinY();
	}

	private int maxX = Integer.MIN_VALUE;
	public int maxX()
	{
		return maxX;
	}
	
	private void calcMaxX(){
		int max = 0;
		for (Point p : getFilledPoints())
			if (p.x > max)
				max = p.x;
		this.maxX = max;
	}

	public int maxY()
	{
		return maxY;
	}
	
	private int maxY;
	private void calcMaxY(){
		int max = 0;
		for (Point p : getFilledPoints())
			if (p.y > max)
				max = p.y;
		maxY = max;
	}
	
	public int minX(){
		return minX;
	}
	
	private int minX;
	private void calcMinX(){
		int min = 0;
		for (Point p : getFilledPoints())
			if (p.x < min)
				min = p.x;
		minX = min;
	}
	
	
	public int minY(){
		return minY;
	}
	
	private int minY;
	private void calcMinY(){
		int min = 0;
		for (Point p : getFilledPoints())
			if (p.y < min)
				min = p.y;
		minY = min;
	}
	

	/**
	 * 
	 * @param one
	 * @param orientation
	 * @return true als er een muur tussen ligt
	 * @return false als er geen muur tussen ligt, of het niet weet
	 */
	public boolean wallBetween(Point one, Orientation orientation)
	{

		if (one == null)
			throw new NullPointerException();
		if (getPanelAt(one) == null)
			if (getPanelAt(orientation.addTo(one)) == null)
				return false;
			else
				return getPanelAt(orientation.addTo(one)).hasBorder(
						orientation.opposite());
		return getPanelAt(one).hasBorder(orientation);

	}

	/**
	 * Geeft aan of er een muur ligt tussen de twee punten
	 * 
	 * @pre de twee punten moeten VLAK naast elkaar liggen
	 * @param one
	 * @param two
	 * @return true als er een muur tussen ligt
	 * @return false als er geen muur tussen ligt, of het niet weet
	 */
	public boolean wallBetween(Point one, Point two)
	{
		if (one == null)
			throw new NullPointerException();
		if (two == null)
			throw new NullPointerException();
		for (Orientation orientation : Orientation.values()) {
			Point point = orientation.addTo(one);
			if (point.getX() == two.getX() && point.getY() == two.getY())
				return wallBetween(one, orientation);
		}
		return false; // <-- DIT MAG NOOIT GEBEUREN @pre
	}

	/**
	 * Geeft terug hoeveel punten rond dit punt gekend zijn. (bestaan al)
	 * 
	 * @param point
	 * @return
	 */
	public int nbOfUnknowns(Point point)
	{
		int nbUnknown = 0;
		//Collection<Point> points = getSurrounding(point);
		for (Point current : getSurrounding(point)) {
			if (panels.containsKey(current))
				continue;
			if (wallBetween(point, current))
				continue;
			nbUnknown++;

		}
		return nbUnknown;
	}
	
	/**
	 * Geeft null terug als punten niet naast mekaar gelegen zijn
	 * 
	 * @param 
	 * @return
	 */
	public static Orientation getOrientationBetween(Point one, Point two){
		for (Orientation orientation : Orientation.values()) {
			Point point = orientation.addTo(one);
			if (point.getX() == two.getX() && point.getY() == two.getY())
				return orientation;
		}
		return null;
	}

	public Iterable<Point> getUnfilledPoints() {
		ArrayList<Point> rv = new ArrayList<Point>();
		for(Point p: getFilledPoints())
		{
			for(Orientation o : Orientation.values())
			{
				if(!wallBetween(p, o)&&getPanelAt(o.addTo(p))==null)
					rv.add(o.addTo(p));
			}
		}
		return rv;
	}

}