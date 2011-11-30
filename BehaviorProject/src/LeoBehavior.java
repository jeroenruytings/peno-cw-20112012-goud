import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;


public abstract class LeoBehavior implements Behavior {
	protected static ImprovedDifferentialPilot pilot =  new ImprovedDifferentialPilot(54.25f, 54.75f, 161.6f, Motor.A, Motor.B, false);
	protected static SensorPort lightSensor = SensorPort.S3;
	protected static SensorPort sonarSensor = SensorPort.S2;
	protected static SensorPort leftTouch = SensorPort.S4;
	protected static SensorPort rightTouch = SensorPort.S1;		
	protected static BarcodeReader barcodeReader = new BarcodeReader();

}
