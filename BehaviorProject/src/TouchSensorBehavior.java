
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;


public class TouchSensorBehavior extends LeoBehavior {
	
	TouchSensor left = new TouchSensor(SensorPort.S4);
	TouchSensor right = new TouchSensor(SensorPort.S1);
	boolean suppressed = true;

	@Override
	public void action() {
		suppressed = false;
		if(left.isPressed() && !right.isPressed()){
			pilot.travelArc(200, -250);
		}
		else if(right.isPressed() && !left.isPressed()){
			pilot.travelArc(-200, 250);
		}
		else{
			pilot.travel(-250);
		}
		while (!suppressed && pilot.isMoving())
			Thread.yield();
		pilot.stop();
		
	}

	@Override
	public void suppress() {
		suppressed = true;		
	}

	@Override
	public boolean takeControl() {
		return left.isPressed() || right.isPressed();
	}

	
}