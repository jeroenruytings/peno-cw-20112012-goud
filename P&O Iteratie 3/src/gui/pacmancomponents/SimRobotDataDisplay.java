package gui.pacmancomponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import board.Board;


public class SimRobotDataDisplay extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board myTrack;
	
	public SimRobotDataDisplay()
	{
		this.setBackground(Color.BLACK);
	}
	
	public void setTrack(Board t){
		this.myTrack = t;
	}
	
	private int calculatePanelWidth(){
		if (myTrack == null)
			throw new IllegalStateException("The track is not initialised!");
		return Math.min(this.getHeight() / myTrack.maxY(), this.getWidth() / myTrack.maxX());
	}
	
	private Point calculateInitialPosition(){
		Point result = new Point((this.getWidth() - calculatePanelWidth() * myTrack.maxX()) / 2,(this.getHeight() - calculatePanelWidth() * myTrack.maxY()) / 2);
		return result;
	}
	
	/**
	 * Code to draw the canvas, given it's SimRobotData.
	 */
	public void paint(Graphics g){
		super.paint(g);
		
		BoardDisplayer td = new BoardDisplayer();
		td.drawBoard(g, myTrack, calculateInitialPosition(), calculatePanelWidth());
	}
	
	/**
	 * Repaint the Canvas.
	 */
	public void repaint(){
		this.paint(this.getGraphics());
	}

	
	
}
