package interfaces.pacmancomponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import util.board.Board;
import util.enums.Orientation;
import util.world.RobotData;

public abstract class BoardDisplay extends Canvas
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<RobotData> robots = new ArrayList<RobotData>();

	private Graphics bufferedGraphics;
	private Image bufferedImage;
	
	
	public static Color getRandomColor(){
		Random r = new Random();
		return new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
	}
	public BoardDisplay()
	{
		super();
		this.setBackground(Color.BLACK);
	}

	public abstract Board getBoard();
	
	private Board getBoardToDraw()
	{
		return fix(getBoard());
	}
	
	private Board fix(Board boardToFix)
	{
		Board rv = new Board();
		int minx = boardToFix.minX();
		int miny=boardToFix.minY();
		for(Point point:boardToFix.getFilledPoints())
		{
			rv.add(boardToFix.getPanelAt(point), new Point(point.x-minx,point.y-miny));
		}
		return rv;
	}

	public int getDrawHeight(){
		return getHeight() - getHeight() / 10;
	}
	
	public int getDrawWidth(){
		return getWidth() - getWidth() / 10;
	}

	private int calculatePanelWidth()
	{
		if (getBoardToDraw() == null)
			throw new IllegalStateException("The track is not initialised!");
		return Math.min(getDrawHeight() / (getBoardToDraw().maxY() + 1),
				getDrawWidth() / (getBoardToDraw().maxX() + 1));
	}

	private int getSpacing()
	{
		return calculatePanelWidth() / 5;

	}

	private Point calculateInitialPosition()
	{
		Point result = new Point(
				(getWidth() - (calculatePanelWidth() * (getBoardToDraw().maxX() + 1))) / 2,
				(getHeight() - (calculatePanelWidth() * (getBoardToDraw().maxY() + 1))) / 2);
		return result;
	}

	/**
	 * Code to draw the canvas, given it's SimRobotData.
	 */
	@Override
	public void paint(Graphics g)
	{
			bufferedImage = createImage(getWidth(), getHeight());
			bufferedGraphics = bufferedImage.getGraphics();

			bufferedGraphics.clearRect(0, 0, getWidth(), getHeight());
			drawBoard(bufferedGraphics);
			for (RobotData r : robots){
				drawRobot(bufferedGraphics, r.getPosition(), r.getOrientation(), r.getRobotColor());
			drawPacman(bufferedGraphics, r.getPacmanLastSighted(), r.getRobotColor());
			}
			g.drawImage(bufferedImage, 0,0,this);
		
	}

	@Override
	public void update(Graphics g){
		paint(g);
	}

	/**
	 * Draw the board of the RobotData object.
	 */
	private void drawBoard(Graphics g)
	{
		if (getBoard() != null){
		BoardDrawer.drawBoard(g, getBoardToDraw(), calculateInitialPosition(),
				calculatePanelWidth());
		}
	}

	/**
	 * Read an image from the resources.
	 * 
	 * @param name
	 *            Name of the image (with the extention). The file must be in
	 *            the resources package, of this project.
	 * @return Image object, representing the file.
	 */
	private Image getImage(String name)
	{
		Image img = null;
		try {
			img = ImageIO.read(RobotData.class
					.getResource("/resources/" + name).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	
	/**
	 * Draw robot on graphics g, the dimensions are calculated with the panelwidth.
	 * @note	The color of the robot is equal to the robotcolor.
	 * @param 	g
	 * 				Graphics to draw on.
	 */
	protected void drawRobot(Graphics g, Point position, Orientation orient, Color robotColor)
	{
		if (position != null) {
			Point panelConvertedAxisCoordinate = new Point(convert(position).x,getBoardToDraw().maxY() - convert(position).y);
			int xDraw = (calculateInitialPosition().x
					+ (calculatePanelWidth() * panelConvertedAxisCoordinate.x) + getSpacing());
			int yDraw = (calculateInitialPosition().y
					+ (calculatePanelWidth() * (panelConvertedAxisCoordinate.y)) + getSpacing());
			g.drawImage(getGhostImage(orient),
					xDraw,
					yDraw,

					calculatePanelWidth() - 2 * getSpacing(),
					calculatePanelWidth() - 2 * getSpacing(), robotColor, this);
		}
	}
	
	
	private Point convert(Point position)
	{
		int minx = getBoard().minX();
		int miny = getBoard().minY();
		return new Point(position.x-minx,position.y-miny);
		
	}

	public Image getGhostImage(Orientation direction){
		switch (direction) {
		case NORTH:
			return getImage("ghostUP.png");
		case EAST:
			return getImage("ghostRight.png");
		case SOUTH:
			return getImage("ghostDown.png");
		case WEST:
			return getImage("ghostLeft.png");
		}
		return null;
	}

	
	/**
	 * Draw pacman on graphics g, the dimensions are calculated with the panelwidth.
	 * @param 	g
	 * 				Graphics to draw on.
	 */
	protected void drawPacman(Graphics g, Point position, Color color)
	{
		if (position != null) {
			Point panelConvertedAxisCoordinate = new Point(convert(position).x,getBoardToDraw().maxY() - convert(position).y);
			Color original = g.getColor();
			g.setColor(color);
			g.fillOval(
					(calculateInitialPosition().x
							+ (calculatePanelWidth() * panelConvertedAxisCoordinate.x) + getSpacing()),
					(calculateInitialPosition().y
							+ (calculatePanelWidth() * panelConvertedAxisCoordinate.y) + getSpacing()),
					calculatePanelWidth() - 2 * getSpacing(),
					calculatePanelWidth() - 2 * getSpacing());
			g.setColor(getBackground());
			g.fillArc(
					(calculateInitialPosition().x
							+ (calculatePanelWidth() * panelConvertedAxisCoordinate.x) + getSpacing()),
					(calculateInitialPosition().y
							+ (calculatePanelWidth() * panelConvertedAxisCoordinate.y) + getSpacing()),
					calculatePanelWidth() - 2 * getSpacing(),
					calculatePanelWidth() - 2 * getSpacing(), 50, -80);
			g.setColor(original);
		}
	}
	
	public void addRobotData(RobotData robot){
		robots.add(robot);
	}
	
	protected List<RobotData> getRobots(){
		return robots;
	}

}