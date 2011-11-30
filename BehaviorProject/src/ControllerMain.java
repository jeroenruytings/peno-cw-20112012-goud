import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class ControllerMain {
	private static MuurUpdater mu = new MuurUpdater();
	private static GoStraightBehavior goStraightBehavior = new GoStraightBehavior();
	private static TouchSensorBehavior touchSensorBehavior = new TouchSensorBehavior();
	private static SonarBehavior sonarBehavior = new SonarBehavior();
	private static LightSensorBehavior lightSensorBehavior = new LightSensorBehavior();
	private static Behavior[] behaviorList = {goStraightBehavior,lightSensorBehavior, sonarBehavior, touchSensorBehavior};
	private static Arbitrator arbitrator = new Arbitrator(behaviorList, false);
	
	public static void main(String args[]){
		System.out.println("Zet de GUI aan");
		Communicator.instance();
		Calibrate.getInstance();
		Calibrate.calibrate();
		mu.start();
		arbitrator.start();
	}

}
