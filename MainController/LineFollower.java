package MainController;

import lejos.nxt.Button;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;


public class LineFollower {

	int color;
	int turn;
	int treshold;
	int min;
	int max;
	
	public static LightSensor sensor = new LightSensor(SensorPort.S4, true);
	
//	ImprovedDifferentialPilot pilot = new ImprovedDifferentialPilot(56f, 55.5f, 113f, Motor.A, Motor.B, false);
	
	public LineFollower(){
		color = 0;
		turn = 0;
		treshold = 0;
		min = (int) Double.POSITIVE_INFINITY;
		max = (int) Double.NEGATIVE_INFINITY;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LineFollower lf = new LineFollower();
		
		lf.calibrate();
		showValues();
		
	//	lf.findLineEdge();
		

	}
	
	public void calibrate() {
		color = sensor.getLightValue();
		min = color;
		max = color;
		
	//	pilot.rotate(-45);
		LCD.drawString("press enter", 0,0);
		Button.ENTER.waitForPressAndRelease();
		color = sensor.getLightValue();
		if (color > max)
			max = color;
		else if (color < min)
			min = color;
		
		treshold = (min+max)/2;
	}
	
//	public void findLineEdge(){
//		
//		while(Math.abs(sensor.getLightValue() - treshold) > 0)
////			pilot.rotate(1);
//	}
	
	public void followLine(){
		while(true){
			color = sensor.getLightValue();
			
			turn = 0;
		}
	}
	
	public static void showValues(){
		while (true){
			LCD.drawString("Value" + sensor.getLightValue(),0,0);
			LCD.drawString("High: " + sensor.getHigh(), 0, 1);
			LCD.drawString("Low: " + sensor.getLow(), 0, 2);
			LCD.clear();
		}
	}
	

}