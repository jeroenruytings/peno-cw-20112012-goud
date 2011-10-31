import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.proposal.DifferentialPilot;


public class testDistance {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DifferentialPilot pilot = new DifferentialPilot(55.2f, 54.8f,113f, Motor.A, Motor.B, false);
	    UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
	    
	    LCD.drawInt(sonic.getDistance(), 0, 0);
	}

}
