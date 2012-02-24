package gui.pacmancomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import board.Board;
import board.Panel;
import board.Panel.Direction;


public class TrackDisplayer {
	
	private static final int AWAYFROMBORDER = 5;
	private static final int SPACE = 4;
	private static final Color color = Color.BLUE;
	
	public Image getTrackImage(Board t, int height, int width){
		return null;
	}
	
	/**
	 * Draw the given panel, starting at position.
	 * @param 	g
	 * 				The graphics to draw on.
	 * @param 	t
	 * 				The track to draw.	
	 * @param 	position
	 * 				The position to start drawing, the upper left corner.
	 * @param 	panelHeight
	 * 				Panels are drawn as squares, the height is equal to the width.	
	 */
	public static void drawTrack(Graphics g, Board t, Point position, int panelHeight){
		for(Point tmp : t.getFilledPoints())
			drawPanel(g, t, position, tmp, panelHeight,panelHeight);
	}

	
	/**
	 * This method draws a wall on the given graphics object.
	 * @param 	g
	 * 				The graphics object to draw on.
	 * @param 	initialposition
	 * 				The 0,0 coordinate where the wall should be drawn.
	 * @param 	height
	 * 				The height of the wall.
	 * @param 	width
	 * 				The width of the wall.
	 * @param 	direction
	 * 				The direction the wall blocks.
	 */
	public static void drawLine(Graphics g, Point initialposition, int height, int width, Direction direction){
		if(direction != null){
			Color original = g.getColor();
			g.setColor(color);
			switch (direction){
			case UP:
				g.drawLine(initialposition.x + (width / AWAYFROMBORDER), initialposition.y, initialposition.x + width - (width / AWAYFROMBORDER), initialposition.y);
				break;
			case RIGHT:
				g.drawLine(initialposition.x + width, initialposition.y + (height / AWAYFROMBORDER), initialposition.x + width, initialposition.y + height - (height / AWAYFROMBORDER));
				break;
			case DOWN:
				g.drawLine(initialposition.x + (width / AWAYFROMBORDER), initialposition.y + height, initialposition.x + width - (width / AWAYFROMBORDER), initialposition.y + height);
				break;
			case LEFT:
				g.drawLine(initialposition.x, initialposition.y + (height / AWAYFROMBORDER), initialposition.x, initialposition.y + height - (height / AWAYFROMBORDER));
				break;
			}
			g.setColor(original);
		}
	}
	
	/**
	 * Draws a given panel on the given graphics object.
	 * @param 	g
	 * 				The graphics to draw on.
	 * @param 	t
	 * 				The Track the panel belongs  to.	
	 * @param 	panelCoordinate
	 * 				The coordinate of the panel on the track.	
	 * @param 	panelHeight
	 * 				The height of the panel in pixels.
	 * @param panelWidth
	 * 				The width of the panel in pixels.
	 */
	public static void  drawPanel(Graphics g, Board t,Point initialCoordinate ,Point panelCoordinate, int panelHeight, int panelWidth){
		// Get the borders.
		Direction[] borderDirections = new Direction[4];
		for(Direction border : Direction.values()){
			if (t.getPanelAt(panelCoordinate).getBorder(border))
				borderDirections[border.ordinal()] = border;
		}
		// Get the null directions.
		Direction[] nullDirections = new Direction[4];
		for(Direction d : borderDirections){
			if ((getPanelInDirection(panelCoordinate, t, d) == null) && (d != null))
				nullDirections[d.ordinal()] = d;
		}
		// Draw the null Directions.
		Point initialCoord = new Point(initialCoordinate.x + (panelCoordinate.x * panelWidth),initialCoordinate.y + (panelCoordinate.y * panelHeight));
		for(Direction tmpDirection : nullDirections){
			if (tmpDirection != null){
				drawLine(g,initialCoord, panelHeight, panelWidth, tmpDirection);
				for (Direction tmp : nullDirections){
					if ((tmpDirection.opposite() != tmp)&&(tmp != tmpDirection)&&(tmp != null))
						drawCorner(g, t,initialCoordinate, panelCoordinate, panelHeight, panelWidth, tmp, tmpDirection, false);
				}
			}
		}

		// Draw the borders.
		for (Direction tmpBorder : borderDirections){
			if (tmpBorder != null){
				Point newInitial = new Point(initialCoord.x + SPACE, initialCoord.y + SPACE); 
			drawLine(g, newInitial, panelHeight - (2* SPACE), panelWidth - (2*SPACE) , tmpBorder);
				for (Direction tmp : borderDirections){
					if ((tmpBorder.opposite()!=tmp)&&(tmp!=tmpBorder)&&(tmp != null))
						drawCorner(g, t,initialCoordinate, panelCoordinate, panelHeight, panelWidth, tmp, tmpBorder, true);
				}
			}
		}
					
	}
	
	/**
	 * This method draws the corner of a panel.
	 * @param g
	 * @param t
	 * @param initialCoordinate
	 * @param panelCoordinate
	 * @param panelHeight
	 * @param panelWidth
	 * @param direction1
	 * @param direction2
	 */
	public static void drawCorner(Graphics g, Board t, Point initialCoordinate, Point panelCoordinate, int panelHeight, int panelWidth, Direction direction1, Direction direction2, boolean inner){
		Color original = g.getColor();
		g.setColor(color);
		Point startCoord = new Point(0, 0);
		int startAngle = 0;
		if ((direction1 == Direction.UP && direction2 == Direction.LEFT) || (direction1 == Direction.LEFT && direction2 == Direction.UP)){
			startCoord = new Point(initialCoordinate.x + (panelCoordinate.x * panelWidth), initialCoordinate.y + (panelCoordinate.y * panelHeight));
			startAngle = 180;
		}
		if ((direction1 == Direction.UP && direction2 == Direction.RIGHT) || (direction1 == Direction.RIGHT && direction2 == Direction.UP))
		{
			startCoord = new Point(initialCoordinate.x + (panelWidth - (2 * (panelWidth/AWAYFROMBORDER))) + (panelCoordinate.x * panelWidth),
					initialCoordinate.y  + (panelCoordinate.y * panelHeight));
			startAngle = 90;
		}
		if ((direction1 == Direction.DOWN && direction2 == Direction.LEFT) || (direction1 == Direction.LEFT && direction2 == Direction.DOWN))
		{
			startCoord = new Point(initialCoordinate.x + (panelCoordinate.x * panelWidth),
					initialCoordinate.y + panelHeight - (2 * (panelHeight/AWAYFROMBORDER)) + (panelCoordinate.y * panelHeight));
			startAngle = 270;
		}
		if ((direction1 == Direction.DOWN && direction2 == Direction.RIGHT) || (direction1 == Direction.RIGHT && direction2 == Direction.DOWN))
		{
			startCoord = new Point(initialCoordinate.x + (panelWidth - (2 * (panelWidth/AWAYFROMBORDER))) + (panelCoordinate.x * panelWidth),
					initialCoordinate.y + panelHeight - (2 * (panelHeight/AWAYFROMBORDER)) + (panelCoordinate.y * panelHeight));
			startAngle = 0;
		}
		if (!inner)
			g.drawArc(startCoord.x, startCoord.y, 2 * (panelWidth / AWAYFROMBORDER), 2 * (panelHeight / AWAYFROMBORDER), startAngle, -90);
		else
			g.drawArc(startCoord.x + SPACE, startCoord.y + SPACE, 2 * (panelWidth / AWAYFROMBORDER) - 2*SPACE, 2 * (panelHeight / AWAYFROMBORDER) - 2*SPACE, startAngle, -90);
		g.setColor(original);
	}
	
	public static void drawReverseCorner(Graphics g, Board t, Point initialCoord, Point panelCoord, int panelHeight, int panelWidth, Direction direction1, Direction direction2, boolean nullCorner){
		Color original = g.getColor();
		g.setColor(color);
		Point startCoord = new Point(0, 0);
		int startAngle = 0;
		if ((direction1 == Direction.UP && direction2 == Direction.LEFT) || (direction1 == Direction.LEFT && direction2 == Direction.UP)){
			startCoord = new Point(initialCoord.x + (panelCoord.x * panelWidth) - (panelWidth / AWAYFROMBORDER), 
					initialCoord.y + (panelCoord.y * panelHeight) - (panelHeight / AWAYFROMBORDER));
			startAngle = 0;
		}
		if ((direction1 == Direction.UP && direction2 == Direction.RIGHT) || (direction1 == Direction.RIGHT && direction2 == Direction.UP))
		{
			startCoord = new Point(initialCoord.x + (panelWidth - (panelWidth/AWAYFROMBORDER)) + (panelCoord.x * panelWidth),
					initialCoord.y  + (panelCoord.y * panelHeight) - (panelHeight / AWAYFROMBORDER));
			startAngle = 270;
		}
		if ((direction1 == Direction.DOWN && direction2 == Direction.LEFT) || (direction1 == Direction.LEFT && direction2 == Direction.DOWN))
		{
			startCoord = new Point(initialCoord.x + (panelCoord.x * panelWidth) - (panelWidth / AWAYFROMBORDER),
					initialCoord.y + panelHeight - ((panelHeight/AWAYFROMBORDER)) + (panelCoord.y * panelHeight));
			startAngle = 90;
		}
		if ((direction1 == Direction.DOWN && direction2 == Direction.RIGHT) || (direction1 == Direction.RIGHT && direction2 == Direction.DOWN))
		{
			startCoord = new Point(initialCoord.x + (panelWidth - (panelWidth/AWAYFROMBORDER)) + (panelCoord.x * panelWidth),
					initialCoord.y + panelHeight - ((panelHeight/AWAYFROMBORDER)) + (panelCoord.y * panelHeight));
			startAngle = 180;
		}
		if (nullCorner)
			g.drawArc(startCoord.x, startCoord.y, 2 * (panelWidth / AWAYFROMBORDER), 2 * (panelHeight / AWAYFROMBORDER), startAngle, -90);
		g.drawArc(startCoord.x - SPACE, startCoord.y - SPACE, 2 * ((panelWidth / AWAYFROMBORDER) + SPACE), 2 * ((panelHeight / AWAYFROMBORDER) + SPACE), startAngle, -90);
		g.setColor(original);
		
		//TODO
		
	}
	
	/**
	 * This method returns the panel in the given direction of the given coordinate on the given track.
	 * @param 	panelCoord
	 * 				The coordinate of the panel to check, on track t.
	 * @param 	t
	 * 				The track the coordinates belong to.
	 * @param 	direction
	 * 				The direction to check.
	 * @return
	 * 			The panel in the given direction.
	 * @note	The panel could be null.
	 */
	private static Panel getPanelInDirection(Point panelCoord, Board t, Direction direction){
		if (direction == null)
			return null;
		if ((t == null) || (panelCoord == null))
			throw new IllegalArgumentException("Track or Coord is null!!!");
		switch (direction){
		case UP:
			return t.getPanelAt(new Point(panelCoord.x,(panelCoord.y - 1)));
		case DOWN:
			return t.getPanelAt(new Point(panelCoord.x,(panelCoord.y + 1)));
		case RIGHT:
			return t.getPanelAt(new Point((panelCoord.x + 1),panelCoord.y));
		case LEFT:
			return t.getPanelAt(new Point((panelCoord.x - 1),panelCoord.y));
		default:
			return null;
		}
		
	}
	
	
}
