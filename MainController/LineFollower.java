package MainController;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;


public class LineFollower {

	int color;
	int turn;
	int treshold;
	int power;
	int min;
	int max;
	
	public static LightSensor sensor = new LightSensor(SensorPort.S4, true);
	
	ImprovedDifferentialPilot pilot = new ImprovedDifferentialPilot(56f, 55.5f, 113f, Motor.A, Motor.B, false);
	
	public LineFollower(){
		color = 0;
		turn = 0;
		treshold = 0;
		power = 90;
		min = (int) Double.POSITIVE_INFINITY;
		max = (int) Double.NEGATIVE_INFINITY;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LineFollower lf = new LineFollower();
		
		lf.calibrate();
		lf.findLineEdge();
		

	}
	
	public void calibrate() {
		color = sensor.readNormalizedValue();
		min = color;
		max = color;
		
		pilot.rotate(-45);
		color = sensor.readNormalizedValue();
		if (color > max)
			max = color;
		else if (color < min)
			min = color;
		
		treshold = (min+max)/2;
	}
	
	public void findLineEdge(){
		
		while(Math.abs(sensor.readNormalizedValue() - treshold) > 0)
			pilot.rotate(1);
	}
	
	public void followLine(){
		while(true){
			color = sensor.readNormalizedValue();
			
			turn = 0;
		}
	}
	
	

}