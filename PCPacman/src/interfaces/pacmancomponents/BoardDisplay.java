package interfaces.pacmancomponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import util.board.Board;
import util.enums.Orientation;
import util.world.RobotData;

public class BoardDisplay extends Canvas
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color robotColor = Color.BLUE;
	private Board board;
	
	private List<RobotData> robots = new ArrayList<RobotData>();
	private Point pacman;

	private Graphics bufferedGraphics;
	private Image bufferedImage;
	
	public BoardDisplay(Board board)
	{
		super();
		this.setBackground(Color.BLACK);
		this.board = board; 
	}
	
	public Color getRobotColor(){
		return robotColor;
	}
	
	protected void setBoard(Board board){
		this.board = board;
	}
	
	public void setRobotColor(Color newColor){
		if (newColor != null){
			this.robotColor = newColor;
		}
	}

	private Board getBoard()
	{
		return fix(board);
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
		if (getBoard() == null)
			throw new IllegalStateException("The track is not initialised!");
		return Math.min(getDrawHeight() / (getBoard().maxY() + 1),
				getDrawWidth() / (getBoard().maxX() + 1));
	}

	private int getSpacing()
	{
		return calculatePanelWidth() / 5;
	}

	private Point calculateInitialPosition()
	{
		Point result = new Point(
				(getWidth() - (calculatePanelWidth() * (getBoard().maxX() + 1))) / 2,
				(getHeight() - (calculatePanelWidth() * (getBoard().maxY() + 1))) / 2);
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
			for (RobotData r : robots)
				drawRobot(bufferedGraphics, r.getPosition(), r.getOrientation());
			drawPacman(bufferedGraphics, getPacman());
			g.drawImage(bufferedImage, 0,0,this);
		
	}
	
	private Point getPacman() {
		return pacman;
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
		BoardDrawer.drawBoard(g, getBoard(), calculateInitialPosition(),
				calculatePanelWidth());
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
	private void drawRobot(Graphics g, Point position, Orientation orient)
	{
		if (position != null) {
			Point panelConvertedAxisCoordinate = new Point(convert(position).x,getBoard().maxY() - convert(position).y);
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
		int minx = board.minX();
		int miny = board.minY();
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
	private void drawPacman(Graphics g, Point position)
	{
		if (position != null) {
			Point panelConvertedAxisCoordinate = new Point(convert(position).x,getBoard().maxY() - convert(position).y);
			Color original = g.getColor();
			g.setColor(Color.YELLOW);
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