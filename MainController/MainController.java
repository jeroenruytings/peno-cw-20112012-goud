import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;


public class MainController {
	public static void main(String[] args) {
		DifferentialPilot P = new DifferentialPilot(56f,55.5f, 116, Motor.A, Motor.B, false);
		P.rotate(360*10);
		
	}
}
