package interfaces.pacmancomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import pacmansystem.board.Board;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Orientation;

public class BoardDisplayer
{

	private static final int AWAYFROMBORDER = 5;
	private static final int SPACE = 4;
	private static final Color color = Color.BLUE;


	public Image getTrackImage(Board t, int height, int width)
	{
		return null;
	}

	/**
	 * Draw the given panel, starting at position.
	 * 
	 * @param g
	 *            The graphics to draw on.
	 * @param t
	 *            The track to draw.
	 * @param position
	 *            The position to start drawing, the NORTHper WEST corner.
	 * @param panelHeight
	 *            Panels are drawn as squares, the height is equal to the width.
	 */
	public static void drawBoard(Graphics g, Board t, Point position,
			int panelHeight)
	{
		for (Point tmp : t.getFilledPoints())
			drawPanel(g, t, position, tmp, panelHeight, panelHeight);
	}

	/**
	 * This method draws a wall on the given graphics object.
	 * 
	 * @param g
	 *            The graphics object to draw on.
	 * @param initialposition
	 *            The 0,0 coordinate where the wall should be drawn.
	 * @param height
	 *            The height of the wall.
	 * @param width
	 *            The width of the wall.
	 * @param Orientation
	 *            The Orientation the wall blocks.
	 */
	public static void drawLine(Graphics g, Point initialposition, int height,
			int width, Orientation Orientation)
	{
		if (Orientation != null) {
			Color original = g.getColor();
			g.setColor(color);
			switch (Orientation)
			{
			case NORTH:
				g.drawLine(initialposition.x + (width / AWAYFROMBORDER),
						initialposition.y, initialposition.x + width
								- (width / AWAYFROMBORDER), initialposition.y);
				break;
			case EAST:
				g.drawLine(initialposition.x + width, initialposition.y
						+ (height / AWAYFROMBORDER), initialposition.x + width,
						initialposition.y + height - (height / AWAYFROMBORDER));
				break;
			case SOUTH:
				g.drawLine(initialposition.x + (width / AWAYFROMBORDER),
						initialposition.y + height, initialposition.x + width
								- (width / AWAYFROMBORDER), initialposition.y
								+ height);
				break;
			case WEST:
				g.drawLine(initialposition.x, initialposition.y
						+ (height / AWAYFROMBORDER), initialposition.x,
						initialposition.y + height - (height / AWAYFROMBORDER));
				break;
			}
			g.setColor(original);
		}
	}

	/**
	 * Draws a given panel on the given graphics object.
	 * 
	 * @param g
	 *            The graphics to draw on.
	 * @param t
	 *            The Track the panel belongs to.
	 * @param panelCoordinate
	 *            The coordinate of the panel on the track.
	 * @param panelHeight
	 *            The height of the panel in pixels.
	 * @param panelWidth
	 *            The width of the panel in pixels.
	 */
	public static void drawPanel(Graphics g, Board t, Point initialCoordinate,
			Point panelCoordinate, int panelHeight, int panelWidth)
	{
		// Get the borders.
		Orientation[] borderOrientations = new Orientation[4];
		for (Orientation border : Orientation.values()) {
			if (t.getPanelAt(panelCoordinate).getBorder(border))
				borderOrientations[border.ordinal()] = border;
		}
		// Get the null Orientations.
		Orientation[] nullOrientations = new Orientation[4];
		for (Orientation d : borderOrientations) {
			if ((getPanelInOrientation(panelCoordinate, t, d) == null)
					&& (d != null))
				nullOrientations[d.ordinal()] = d;
		}
		// Draw the null Orientations.
		Point initialCoord = new Point(initialCoordinate.x
				+ (panelCoordinate.x * panelWidth), initialCoordinate.y
				+ (panelCoordinate.y * panelHeight));
		for (Orientation tmpOrientation : nullOrientations) {
			if (tmpOrientation != null) {
				drawLine(g, initialCoord, panelHeight, panelWidth,
						tmpOrientation);
				for (Orientation tmp : nullOrientations) {
					if ((tmpOrientation.opposite() != tmp)
							&& (tmp != tmpOrientation) && (tmp != null))
						drawCorner(g, t, initialCoordinate, panelCoordinate,
								panelHeight, panelWidth, tmp, tmpOrientation,
								false);
				}
			}
		}

		// Draw the borders.
		for (Orientation tmpBorder : borderOrientations) {
			if (tmpBorder != null) {
				Point newInitial = new Point(initialCoord.x + SPACE,
						initialCoord.y + SPACE);
				drawLine(g, newInitial, panelHeight - (2 * SPACE), panelWidth
						- (2 * SPACE), tmpBorder);
				for (Orientation tmp : borderOrientations) {
					if ((tmpBorder.opposite() != tmp) && (tmp != tmpBorder)
							&& (tmp != null))
						drawCorner(g, t, initialCoordinate, panelCoordinate,
								panelHeight, panelWidth, tmp, tmpBorder, true);
				}
			}
		}

	}

	/**
	 * This method draws the corner of a panel.
	 * 
	 * @param g
	 * @param t
	 * @param initialCoordinate
	 * @param panelCoordinate
	 * @param panelHeight
	 * @param panelWidth
	 * @param Orientation1
	 * @param Orientation2
	 */
	public static void drawCorner(Graphics g, Board t, Point initialCoordinate,
			Point panelCoordinate, int panelHeight, int panelWidth,
			Orientation Orientation1, Orientation Orientation2, boolean inner)
	{
		Color original = g.getColor();
		g.setColor(color);
		Point startCoord = new Point(0, 0);
		int startAngle = 0;
		if ((Orientation1 == Orientation.NORTH && Orientation2 == Orientation.WEST)
				|| (Orientation1 == Orientation.WEST && Orientation2 == Orientation.NORTH)) {
			startCoord = new Point(initialCoordinate.x
					+ (panelCoordinate.x * panelWidth), initialCoordinate.y
					+ (panelCoordinate.y * panelHeight));
			startAngle = 180;
		}
		if ((Orientation1 == Orientation.NORTH && Orientation2 == Orientation.EAST)
				|| (Orientation1 == Orientation.EAST && Orientation2 == Orientation.NORTH)) {
			startCoord = new Point(initialCoordinate.x
					+ (panelWidth - (2 * (panelWidth / AWAYFROMBORDER)))
					+ (panelCoordinate.x * panelWidth), initialCoordinate.y
					+ (panelCoordinate.y * panelHeight));
			startAngle = 90;
		}
		if ((Orientation1 == Orientation.SOUTH && Orientation2 == Orientation.WEST)
				|| (Orientation1 == Orientation.WEST && Orientation2 == Orientation.SOUTH)) {
			startCoord = new Point(initialCoordinate.x
					+ (panelCoordinate.x * panelWidth), initialCoordinate.y
					+ panelHeight - (2 * (panelHeight / AWAYFROMBORDER))
					+ (panelCoordinate.y * panelHeight));
			startAngle = 270;
		}
		if ((Orientation1 == Orientation.SOUTH && Orientation2 == Orientation.EAST)
				|| (Orientation1 == Orientation.EAST && Orientation2 == Orientation.SOUTH)) {
			startCoord = new Point(initialCoordinate.x
					+ (panelWidth - (2 * (panelWidth / AWAYFROMBORDER)))
					+ (panelCoordinate.x * panelWidth), initialCoordinate.y
					+ panelHeight - (2 * (panelHeight / AWAYFROMBORDER))
					+ (panelCoordinate.y * panelHeight));
			startAngle = 0;
		}
		if (!inner)
			g.drawArc(startCoord.x, startCoord.y,
					2 * (panelWidth / AWAYFROMBORDER),
					2 * (panelHeight / AWAYFROMBORDER), startAngle, -90);
		else
			g.drawArc(startCoord.x + SPACE, startCoord.y + SPACE, 2
					* (panelWidth / AWAYFROMBORDER) - 2 * SPACE, 2
					* (panelHeight / AWAYFROMBORDER) - 2 * SPACE, startAngle,
					-90);
		g.setColor(original);
	}

	public static void drawReverseCorner(Graphics g, Board t,
			Point initialCoord, Point panelCoord, int panelHeight,
			int panelWidth, Orientation Orientation1, Orientation Orientation2,
			boolean nullCorner)
	{
		Color original = g.getColor();
		g.setColor(color);
		Point startCoord = new Point(0, 0);
		int startAngle = 0;
		if ((Orientation1 == Orientation.NORTH && Orientation2 == Orientation.WEST)
				|| (Orientation1 == Orientation.WEST && Orientation2 == Orientation.NORTH)) {
			startCoord = new Point(initialCoord.x + (panelCoord.x * panelWidth)
					- (panelWidth / AWAYFROMBORDER), initialCoord.y
					+ (panelCoord.y * panelHeight)
					- (panelHeight / AWAYFROMBORDER));
			startAngle = 0;
		}
		if ((Orientation1 == Orientation.NORTH && Orientation2 == Orientation.EAST)
				|| (Orientation1 == Orientation.EAST && Orientation2 == Orientation.NORTH)) {
			startCoord = new Point(initialCoord.x
					+ (panelWidth - (panelWidth / AWAYFROMBORDER))
					+ (panelCoord.x * panelWidth), initialCoord.y
					+ (panelCoord.y * panelHeight)
					- (panelHeight / AWAYFROMBORDER));
			startAngle = 270;
		}
		if ((Orientation1 == Orientation.SOUTH && Orientation2 == Orientation.WEST)
				|| (Orientation1 == Orientation.WEST && Orientation2 == Orientation.SOUTH)) {
			startCoord = new Point(initialCoord.x + (panelCoord.x * panelWidth)
					- (panelWidth / AWAYFROMBORDER), initialCoord.y
					+ panelHeight - ((panelHeight / AWAYFROMBORDER))
					+ (panelCoord.y * panelHeight));
			startAngle = 90;
		}
		if ((Orientation1 == Orientation.SOUTH && Orientation2 == Orientation.EAST)
				|| (Orientation1 == Orientation.EAST && Orientation2 == Orientation.SOUTH)) {
			startCoord = new Point(initialCoord.x
					+ (panelWidth - (panelWidth / AWAYFROMBORDER))
					+ (panelCoord.x * panelWidth), initialCoord.y + panelHeight
					- ((panelHeight / AWAYFROMBORDER))
					+ (panelCoord.y * panelHeight));
			startAngle = 180;
		}
		if (nullCorner)
			g.drawArc(startCoord.x, startCoord.y,
					2 * (panelWidth / AWAYFROMBORDER),
					2 * (panelHeight / AWAYFROMBORDER), startAngle, -90);
		g.drawArc(startCoord.x - SPACE, startCoord.y - SPACE,
				2 * ((panelWidth / AWAYFROMBORDER) + SPACE),
				2 * ((panelHeight / AWAYFROMBORDER) + SPACE), startAngle, -90);
		g.setColor(original);

		// TODO

	}

	/**
	 * This method returns the panel in the given Orientation of the given
	 * coordinate on the given track.
	 * 
	 * @param panelCoord
	 *            The coordinate of the panel to check, on track t.
	 * @param t
	 *            The track the coordinates belong to.
	 * @param Orientation
	 *            The Orientation to check.
	 * @return The panel in the given Orientation.
	 * @note The panel could be null.
	 */
	private static Panel getPanelInOrientation(Point panelCoord, Board t,
			Orientation Orientation)
	{
		if (Orientation == null)
			return null;
		if ((t == null) || (panelCoord == null))
			throw new IllegalArgumentException("Track or Coord is null!!!");
		switch (Orientation)
		{
		case NORTH:
			return t.getPanelAt(new Point(panelCoord.x, (panelCoord.y - 1)));
		case SOUTH:
			return t.getPanelAt(new Point(panelCoord.x, (panelCoord.y + 1)));
		case EAST:
			return t.getPanelAt(new Point((panelCoord.x + 1), panelCoord.y));
		case WEST:
			return t.getPanelAt(new Point((panelCoord.x - 1), panelCoord.y));
		default:
			return null;
		}

	}

}