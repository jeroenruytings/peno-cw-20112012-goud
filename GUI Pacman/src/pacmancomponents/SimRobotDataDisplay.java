package pacmancomponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class SimRobotDataDisplay extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SimRobotDataDisplay()
	{
		this.setBackground(Color.BLACK);
	}
	
	/**
	 * Code to draw the canvas, given it's SimRobotData.
	 */
	public void paint(Graphics g){
		super.paint(g);
		
		
		// ADD code to draw Track.
	}
	
	/**
	 * Repaint the Canvas.
	 */
	public void repaint(){
		this.paint(this.getGraphics());
	}

	
	
}
