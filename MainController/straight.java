
import lejos.nxt.Motor;

public class straight {
	static ImprovedDifferentialPilot pilot;
	public static void start() {
		pilot = new ImprovedDifferentialPilot(56f, 55.5f, 113f, Motor.A,
				Motor.B, false);
		pilot.setSpeed(720);
		pilot.travel(1000);
	}

}

