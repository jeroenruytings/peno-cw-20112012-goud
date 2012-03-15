package interfaces.pacmancomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;

import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;

public class UltrasonicValuePanel extends Panel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Samplerate per second.
	private static int SAMPLE_RATE = 1;
	
	private MoverLayer moverlayer;
	private int ultrasonic;
	private Label lblUltrasonic;
	
	public UltrasonicValuePanel(MoverLayer moverlayer){
		this.moverlayer = moverlayer;
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		lblUltrasonic = new Label(String.valueOf(this.ultrasonic));
		this.add(lblUltrasonic, BorderLayout.CENTER);
		this.setForeground(Color.WHITE);
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(1000 / SAMPLE_RATE);
			} catch (InterruptedException e) {}
		if (moverlayer != null)
			this.ultrasonic = moverlayer.getUltrasonic();
		this.lblUltrasonic.setText(String.valueOf(this.ultrasonic));
		this.repaint();
		}
	}
	
	
}