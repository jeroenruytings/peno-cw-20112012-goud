import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class Veelhoek {
	static ImprovedDifferentialPilot pilot;
	static int number = 1;
	static int length = 20000;
	/**
	 * Makes the robot drive in a polygon
	 * @param numberOfCorners
	 * 	number of corners of the polygon
	 * @param distance
	 * 	distance between each corner
	 */
	public static void driveInPolygon(int numberOfCorners, float distance) {
		pilot.setSpeed(360);
		//herp derp drive in polygon
		for (int i = 0; i < numberOfCorners; i++) {
			pilot.travel(distance);
			pilot.rotate(360f / numberOfCorners, numberOfCorners);
		}

	}

	private static boolean enterNumber() {
		if (Button.ENTER.isPressed()) {
			Button.ENTER.waitForPressAndRelease();
			return true;
		}

		if (Button.LEFT.isPressed()) {
			number--;
			Button.LEFT.waitForPressAndRelease();
			LCD.clear();
		}
		if (Button.RIGHT.isPressed()) {
			number++;
			Button.RIGHT.waitForPressAndRelease();
			LCD.clear();
		}
		return false;
	}

	private static boolean enterLength() {
		if (Button.ENTER.isPressed()) {
			Button.ENTER.waitForPressAndRelease();
			return true;
		}

		if (Button.LEFT.isPressed()) {
			length -= 100;
			Button.LEFT.waitForPressAndRelease();
			LCD.clear();
		}
		if (Button.RIGHT.isPressed()) {
			length += 100;
			Button.RIGHT.waitForPressAndRelease();
			LCD.clear();
		}
		return false;
	}
	/**
	 * The start of the simulation
	 */
	public static void start() {
		//create improved diff pilot ( has a modified rotate method to adjust 
		// an error the car makes due to something
		pilot = new ImprovedDifferentialPilot(56f, 55.5f, 113f, Motor.A,
				Motor.B, false);
		//get input 
		getNumCornersAndDistance();
		//make the car drive in a polygon with the given number as #corners
		// and distance between each corner
		driveInPolygon(number, length);
	}
	/**
	 * Method gets the number of corners the robot has to make, and the distance the robot has to t
	 * travel between each corner.
	 */
	private static void getNumCornersAndDistance() {
		LCD.clear();
		while (!enterNumber()) {
			LCD.drawString("Aantal hoeken: ", 0, 0);
			LCD.drawInt(number, 0, 1);
		}
		LCD.clear();

		while (!enterLength()) {
			LCD.drawString("Lengte: ", 0, 0);
			LCD.drawInt(length, 0, 1);
		}

		LCD.clear();
	}

}
