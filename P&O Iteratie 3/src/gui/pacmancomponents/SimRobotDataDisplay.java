package gui.pacmancomponents;

import gui.tmp.Coordinate;
import gui.tmp.Track;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;


public class SimRobotDataDisplay extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Track myTrack;
	
	public SimRobotDataDisplay()
	{
		this.setBackground(Color.BLACK);
	}
	
	public void setTrack(Track t){
		this.myTrack = t;
	}
	
	private int calculatePanelWidth(){
		if (myTrack == null)
			throw new IllegalStateException("The track is not initialised!");
		return Math.min(this.getHeight() / myTrack.getHeight(), this.getWidth() / myTrack.getWidth());
	}
	
	private Coordinate calculateInitialPosition(){
		Coordinate result = new Coordinate((this.getWidth() - calculatePanelWidth() * myTrack.getWidth()) / 2,(this.getHeight() - calculatePanelWidth() * myTrack.getHeight()) / 2);
		return result;
	}
	
	/**
	 * Code to draw the canvas, given it's SimRobotData.
	 */
	public void paint(Graphics g){
		super.paint(g);
		
		TrackDisplayer td = new TrackDisplayer();
		td.drawTrack(g, myTrack, calculateInitialPosition(), calculatePanelWidth());
	}
	
	/**
	 * Repaint the Canvas.
	 */
	public void repaint(){
		this.paint(this.getGraphics());
	}

	
	
}
