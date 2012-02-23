package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import board.Panel.Direction;
/**
 * 
 * @author Dieter
 *
 *This class assures that 2 boards that are next to eachother( according to our coordinate system) have no conflicting borders:
 *	 
 */
public class Board {
	private Map<Point, Panel> panels;

	public Board() {
		panels = new HashMap<Point, Panel>();
	}

	public Board(Board board) {
		this.panels = new HashMap<Point, Panel>();
		for (Point p : board.getFilledPoints())
			this.add(board.getPanelAt(p), p);

	}

	/**
	 * Adds a panel and if it is conflicting with the already added panels it throws an exception
	 * 
	 */
	public void add(Panel panel, Point p) {
		if (conflicting(p, panel))
			throw new IllegalArgumentException();
		panels.put(p, panel);
	}
	/**
	 * If a border panel has a different 
	 * @param p
	 * @param panel
	 * @return
	 */
	private boolean conflicting(Point p, Panel panel) {
		for (Direction d : Direction.values())
			if (hasPanelAt(d.addTo(p))) {
				if (panel.getBorder(d) != this.getPanelAt(d.addTo(p))
						.getBorder(d.opposite()))
					return true;
			}
		return false;
	}
	public void addForced(Panel panel,Point point)
	{
		this.panels.put(point,panel);
		for(Direction d:Direction.values())
			if(hasPanelAt(d.addTo(point)))
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

	public void clear() {

	}

	public Iterable<Point> getFilledPoints() {
		return new ArrayList<Point>(panels.keySet());
	}

	@Override
	public Object clone() {
		return new Board(this);
	}

}