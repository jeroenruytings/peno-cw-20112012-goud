package interfaces.pacmancomponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import javax.imageio.ImageIO;
import pacmansystem.board.Board;
import pacmansystem.board.enums.Orientation;
import pacmansystem.world.RobotData;

public class SimRobotDataDisplay extends Canvas
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RobotData myRobotData;
	private Color robotColor = Color.BLUE;

	private Graphics bufferedGraphics;
	private Image bufferedImage;
	private Orientation robotOrientation = Orientation.NORTH;
	
	public SimRobotDataDisplay(RobotData robot)
	{
		super();
		this.setBackground(Color.BLACK);
		myRobotData = robot;
	}
	
	public Color getRobotColor(){
		return robotColor;
	}
	
	public void setRobotColor(Color newColor){
		if (newColor != null){
			this.robotColor = newColor;
		}
	}

	public Board getBoard()
	{
		return myRobotData.getBoard();
	}
	
	public int getDrawHeight(){
		return super.getHeight() - super.getHeight() / 10;
	}
	
	public int getDrawWidth(){
		return super.getWidth() - super.getWidth() / 10;
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
		if (bufferedImage == null){
			bufferedImage = createImage(getWidth(), getHeight());
			bufferedGraphics = bufferedImage.getGraphics();
		}
		
		bufferedGraphics.clearRect(0, 0, getWidth(), getHeight());
		drawBoard(bufferedGraphics);
		drawRobot(bufferedGraphics);
		drawPacman(bufferedGraphics);
		
		
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
		BoardDisplayer.drawBoard(g, getBoard(), calculateInitialPosition(),
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
	private void drawRobot(Graphics g)
	{
		if (myRobotData.getPosition() != null) {
			g.drawImage(getGhostImage(robotOrientation ),
					(int) (calculateInitialPosition().getX()
							+ (calculatePanelWidth() * myRobotData
									.getPosition().getX()) + getSpacing()),
					(int) (calculateInitialPosition().getY()
							+ (calculatePanelWidth() * myRobotData
									.getPosition().getY()) + getSpacing()),

					calculatePanelWidth() - 2 * getSpacing(),
					calculatePanelWidth() - 2 * getSpacing(), robotColor, this);
		}
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
	private void drawPacman(Graphics g)
	{
		if (myRobotData.getPacmanLastSighted() != null) {

			Color original = g.getColor();
			g.setColor(Color.YELLOW);
			g.fillOval(
					(int) (calculateInitialPosition().getX()
							+ (calculatePanelWidth() * myRobotData
									.getPacmanLastSighted().getX()) + getSpacing()),
					(int) (calculateInitialPosition().getY()
							+ (calculatePanelWidth() * myRobotData
									.getPacmanLastSighted().getY()) + getSpacing()),
					calculatePanelWidth() - 2 * getSpacing(),
					calculatePanelWidth() - 2 * getSpacing());
			g.setColor(getBackground());
			g.fillArc(
					(int) (calculateInitialPosition().getX()
							+ (calculatePanelWidth() * myRobotData
									.getPacmanLastSighted().getX()) + getSpacing()),
					(int) (calculateInitialPosition().getY()
							+ (calculatePanelWidth() * myRobotData
									.getPacmanLastSighted().getY()) + getSpacing()),
					calculatePanelWidth() - 2 * getSpacing(),
					calculatePanelWidth() - 2 * getSpacing(), 50, -80);
			g.setColor(original);
		}
	}

}
