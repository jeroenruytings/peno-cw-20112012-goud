
import lejos.nxt.Motor;
//import lejos.robotics.Movement;
import lejos.robotics.TachoMotor;
import lejos.robotics.proposal.DifferentialPilot;


public class ImprovedDifferentialPilot extends DifferentialPilot {
	static ImprovedDifferentialPilot p = new ImprovedDifferentialPilot(54.25f, 54.75f,
			161.6f, Motor.B,
			Motor.A, false);
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
	
	public void setStandardSpeed(int standardSpeed) {
		Motor.A.setSpeed(standardSpeed);
		Motor.B.setSpeed(standardSpeed);
		
	}
	
	public void stopWheel(Motor m){
		m.setSpeed(0);
	}


}