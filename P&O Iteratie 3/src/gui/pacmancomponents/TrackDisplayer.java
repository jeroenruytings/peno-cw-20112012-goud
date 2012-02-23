package gui.pacmancomponents;

import gui.tmp.Coordinate;
import gui.tmp.Direction;
import gui.tmp.Panel;
import gui.tmp.Track;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


public class TrackDisplayer {
	
	private static final int AWAYFROMBORDER = 5;
	private static final int SPACE = 4;
	private static final Color color = Color.BLUE;
	
	public Image getTrackImage(Track t, int height, int width){
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
	public void drawTrack(Graphics g, Track t, Coordinate position, int panelHeight){
		// for(Coordinate tmp : t.getCoordinates()){
		//		drawPanel(g, t, position, tmp, panelHeight,panelHeight);
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
	public static void drawLine(Graphics g, Coordinate initialposition, int height, int width, Direction direction){
		if(direction != null){
			Color original = g.getColor();
			g.setColor(color);
			switch (direction){
			case NORTH:
				g.drawLine(initialposition.getX() + (width / AWAYFROMBORDER), initialposition.getY(), initialposition.getX() + width - (width / AWAYFROMBORDER), initialposition.getY());
				break;
			case EAST:
				g.drawLine(initialposition.getX() + width, initialposition.getY() + (height / AWAYFROMBORDER), initialposition.getX() + width, initialposition.getY() + height - (height / AWAYFROMBORDER));
				break;
			case SOUTH:
				g.drawLine(initialposition.getX() + (width / AWAYFROMBORDER), initialposition.getY() + height, initialposition.getX() + width - (width / AWAYFROMBORDER), initialposition.getY() + height);
				break;
			case WEST:
				g.drawLine(initialposition.getX(), initialposition.getY() + (height / AWAYFROMBORDER), initialposition.getX(), initialposition.getY() + height - (height / AWAYFROMBORDER));
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
	public static void  drawPanel(Graphics g, Track t,Coordinate initialCoordinate ,Coordinate panelCoordinate, int panelHeight, int panelWidth){
		// Get the borders.
		Direction[] borderDirections = new Direction[4];
		for(Direction border : Direction.values()){
			if (t.getPanel(panelCoordinate).hasBorder(border))
				borderDirections[border.ordinal()] = border;
		}
		// Get the null directions.
		Direction[] nullDirections = new Direction[4];
		for(Direction d : borderDirections){
			if ((getPanelInDirection(panelCoordinate, t, d) == null) && (d != null))
				nullDirections[d.ordinal()] = d;
		}
		// Draw the null Directions.
		Coordinate initialCoord = new Coordinate(initialCoordinate.getX() + (panelCoordinate.getX() * panelWidth),initialCoordinate.getY() + (panelCoordinate.getY() * panelHeight));
		for(Direction tmpDirection : nullDirections)
			drawLine(g,initialCoord, panelHeight, panelWidth, tmpDirection);
		// Draw the borders.
		for (Direction tmpBorder : borderDirections){
			if (tmpBorder != null){
			drawLine(g, new Coordinate(initialCoord.getX() + SPACE, initialCoord.getY() + SPACE), panelHeight - (2* SPACE), panelWidth - (2*SPACE) , tmpBorder);
				for (Direction tmp : borderDirections){
					if (tmpBorder.neighbor(tmp))
						drawCorner(g, t, initialCoordinate, panelCoordinate, panelHeight, panelWidth, tmp, tmpBorder);
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
	public static void drawCorner(Graphics g, Track t, Coordinate initialCoordinate, Coordinate panelCoordinate, int panelHeight, int panelWidth, Direction direction1, Direction direction2){
		Color original = g.getColor();
		g.setColor(color);
		Coordinate startCoord = new Coordinate(0, 0);
		int startAngle = 0;
		if ((direction1 == Direction.NORTH && direction2 == Direction.WEST) || (direction1 == Direction.WEST && direction2 == Direction.NORTH)){
			startCoord = new Coordinate(initialCoordinate.getX() + (panelCoordinate.getX() * panelWidth), initialCoordinate.getY() + (panelCoordinate.getY() * panelHeight));
			startAngle = 180;
		}
		if ((direction1 == Direction.NORTH && direction2 == Direction.EAST) || (direction1 == Direction.EAST && direction2 == Direction.NORTH))
		{
			startCoord = new Coordinate(initialCoordinate.getX() + (panelWidth - (2 * (panelWidth/AWAYFROMBORDER))) + (panelCoordinate.getX() * panelWidth),
					initialCoordinate.getY()  + (panelCoordinate.getY() * panelHeight));
			startAngle = 90;
		}
		if ((direction1 == Direction.SOUTH && direction2 == Direction.WEST) || (direction1 == Direction.WEST && direction2 == Direction.SOUTH))
		{
			startCoord = new Coordinate(initialCoordinate.getX() + (panelCoordinate.getX() * panelWidth),
					initialCoordinate.getY() + panelHeight - (2 * (panelHeight/AWAYFROMBORDER)) + (panelCoordinate.getY() * panelHeight));
			startAngle = 270;
		}
		if ((direction1 == Direction.SOUTH && direction2 == Direction.EAST) || (direction1 == Direction.EAST && direction2 == Direction.SOUTH))
		{
			startCoord = new Coordinate(initialCoordinate.getX() + (panelWidth - (2 * (panelWidth/AWAYFROMBORDER))) + (panelCoordinate.getX() * panelWidth),
					initialCoordinate.getY() + panelHeight - (2 * (panelHeight/AWAYFROMBORDER)) + (panelCoordinate.getY() * panelHeight));
			startAngle = 0;
		}
		
		g.drawArc(startCoord.getX(), startCoord.getY(), 2 * (panelWidth / AWAYFROMBORDER), 2 * (panelHeight / AWAYFROMBORDER), startAngle, -90);
		g.setColor(original);
	}
	
	public static void drawReverseCorner(Graphics g, Track t, Coordinate initialCoord, Coordinate panelCoord, int panelHeight, int panelWidth, Direction direction1, Direction direction2, boolean nullCorner){
		Color original = g.getColor();
		g.setColor(color);
		Coordinate startCoord = new Coordinate(0, 0);
		int startAngle = 0;
		if ((direction1 == Direction.NORTH && direction2 == Direction.WEST) || (direction1 == Direction.WEST && direction2 == Direction.NORTH)){
			startCoord = new Coordinate(initialCoord.getX() + (panelCoord.getX() * panelWidth) - (panelWidth / AWAYFROMBORDER), 
					initialCoord.getY() + (panelCoord.getY() * panelHeight) - (panelHeight / AWAYFROMBORDER));
			startAngle = 0;
		}
		if ((direction1 == Direction.NORTH && direction2 == Direction.EAST) || (direction1 == Direction.EAST && direction2 == Direction.NORTH))
		{
			startCoord = new Coordinate(initialCoord.getX() + (panelWidth - (panelWidth/AWAYFROMBORDER)) + (panelCoord.getX() * panelWidth),
					initialCoord.getY()  + (panelCoord.getY() * panelHeight) - (panelHeight / AWAYFROMBORDER));
			startAngle = 270;
		}
		if ((direction1 == Direction.SOUTH && direction2 == Direction.WEST) || (direction1 == Direction.WEST && direction2 == Direction.SOUTH))
		{
			startCoord = new Coordinate(initialCoord.getX() + (panelCoord.getX() * panelWidth) - (panelWidth / AWAYFROMBORDER),
					initialCoord.getY() + panelHeight - ((panelHeight/AWAYFROMBORDER)) + (panelCoord.getY() * panelHeight));
			startAngle = 90;
		}
		if ((direction1 == Direction.SOUTH && direction2 == Direction.EAST) || (direction1 == Direction.EAST && direction2 == Direction.SOUTH))
		{
			startCoord = new Coordinate(initialCoord.getX() + (panelWidth - (panelWidth/AWAYFROMBORDER)) + (panelCoord.getX() * panelWidth),
					initialCoord.getY() + panelHeight - ((panelHeight/AWAYFROMBORDER)) + (panelCoord.getY() * panelHeight));
			startAngle = 180;
		}
		if (nullCorner)
			g.drawArc(startCoord.getX(), startCoord.getY(), 2 * (panelWidth / AWAYFROMBORDER), 2 * (panelHeight / AWAYFROMBORDER), startAngle, -90);
		g.drawArc(startCoord.getX() - SPACE, startCoord.getY() - SPACE, 2 * ((panelWidth / AWAYFROMBORDER) + SPACE), 2 * ((panelHeight / AWAYFROMBORDER) + SPACE), startAngle, -90);
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
	private static Panel getPanelInDirection(Coordinate panelCoord, Track t, Direction direction){
		if (direction == null)
			return null;
		if ((t == null) || (panelCoord == null))
			throw new IllegalArgumentException("Track or Coord is null!!!");
		switch (direction){
		case NORTH:
			return t.getPanel(new Coordinate(panelCoord.getX(),(panelCoord.getY() - 1)));
		case SOUTH:
			return t.getPanel(new Coordinate(panelCoord.getX(),(panelCoord.getY() + 1)));
		case EAST:
			return t.getPanel(new Coordinate((panelCoord.getX() + 1),panelCoord.getY()));
		case WEST:
			return t.getPanel(new Coordinate((panelCoord.getX() - 1),panelCoord.getY()));
		default:
			return null;
		}
		
	}
	
	
}
