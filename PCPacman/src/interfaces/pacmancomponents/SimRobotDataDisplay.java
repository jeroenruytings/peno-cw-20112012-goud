package interfaces.pacmancomponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

import pacmansystem.board.Board;
import pacmansystem.world.RobotData;

public class SimRobotDataDisplay extends Canvas
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RobotData myRobotData;
	private Color robotColor = Color.GRAY;

	public SimRobotDataDisplay(RobotData robot)
	{
		super();
		this.setBackground(Color.BLACK);
		myRobotData = robot;
	}

	public Board getBoard()
	{
		return myRobotData.getBoard();
	}

	private int calculatePanelWidth()
	{
		if (getBoard() == null)
			throw new IllegalStateException("The track is not initialised!");
		return Math.min(this.getHeight() / (getBoard().maxY() + 1),
				this.getWidth() / (getBoard().maxX() + 1));
	}

	private int getSpacing()
	{
		return calculatePanelWidth() / 5;
	}

	private Point calculateInitialPosition()
	{
		Point result = new Point(
				(this.getWidth() - (calculatePanelWidth() * (getBoard().maxX() + 1))) / 2,
				(this.getHeight() - (calculatePanelWidth() * (getBoard().maxY() + 1))) / 2);
		return result;
	}

	/**
	 * Code to draw the canvas, given it's SimRobotData.
	 */
	public void paint(Graphics g)
	{
		drawBoard(g);
		drawRobot(g);
		drawPacman(g);
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

	private void drawRobot(Graphics g)
	{
		if (myRobotData.getPosition() != null) {
			g.drawImage(getImage("ghost.png"),
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
