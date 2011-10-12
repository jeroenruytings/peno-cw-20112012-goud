import lejos.robotics.Movement;
import lejos.robotics.TachoMotor;
import lejos.robotics.proposal.DifferentialPilot;


public class ImprovedDifferentialPilot extends DifferentialPilot {

	public ImprovedDifferentialPilot(float leftWheelDiameter,
			float rightWheelDiameter, float trackWidth, TachoMotor leftMotor,
			TachoMotor rightMotor, boolean reverse) {
		super(leftWheelDiameter, rightWheelDiameter, trackWidth, leftMotor, rightMotor,
				reverse);
	}
	/**
	 * Rotate a certain degree with a correction.
	 * @param d
	 * 	The number of degrees the robot has to turn on the spot.
	 * @param num
	 * 	The number of corners the robot has to make, this gives us the best results.
	 */
	public void rotate(float d,float num)
	{
		 super.rotate((float) (d-0.4*num));
	}

}
