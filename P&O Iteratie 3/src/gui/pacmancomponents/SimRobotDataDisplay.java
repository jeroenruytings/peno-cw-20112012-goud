package gui.pacmancomponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import board.Board;
import board.RobotBoard;


public class SimRobotDataDisplay extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RobotBoard myRobotData; 
	
	public SimRobotDataDisplay(RobotBoard rb)
	{
		super();
		this.setBackground(Color.BLACK);
		myRobotData = rb;
	}
	
	public Board getBoard(){
		return myRobotData.getRelativeBoard();
	}
	
	private int calculatePanelWidth(){
		if (getBoard() == null)
			throw new IllegalStateException("The track is not initialised!");
		return Math.min(this.getHeight() / (getBoard().maxY() + 1), this.getWidth() / (getBoard().maxX() + 1));
	}
	
	private Point calculateInitialPosition(){
		Point result = new Point((this.getWidth() - (calculatePanelWidth() * (getBoard().maxX() + 1))) / 2,(this.getHeight() - (calculatePanelWidth() * (getBoard().maxY() + 1))) / 2);
		return result;
	}
	
	/**
	 * Code to draw the canvas, given it's SimRobotData.
	 */
	public void paint(Graphics g){
		BoardDisplayer.drawBoard(g, getBoard(), calculateInitialPosition(), calculatePanelWidth());
	}
	
}
