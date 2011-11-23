import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class ControllerMain {
	
	private static GoStraightBehavior goStraightBehavior = new GoStraightBehavior();
	private static TouchSensorBehavior touchSensorBehavior = new TouchSensorBehavior();
	private static SonarBehavior sonarBehavior = new SonarBehavior();
	private static LightSensorBehavior ligthSensorBehavior = new LightSensorBehavior();
	private static Behavior[] behaviorList = {goStraightBehavior,ligthSensorBehavior,touchSensorBehavior};
	private static Arbitrator arbitrator = new Arbitrator(behaviorList, false);
	
	public static void main(String args[]){
		
		Calibrate.getInstance();
		Calibrate.calibrate();
		arbitrator.start();
	}

}
