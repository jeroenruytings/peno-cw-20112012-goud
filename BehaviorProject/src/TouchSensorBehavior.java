
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;


public class TouchSensorBehavior extends LeoBehavior {
	
	TouchSensor left = new TouchSensor(SensorPort.S4);
	TouchSensor right = new TouchSensor(SensorPort.S1);
	boolean suppressed = true;
	boolean rightPressed = false;
	boolean leftPressed = false;

	private BehaviourReason sensorsPressed(){
		if(leftPressed&&rightPressed)
			return BehaviourReason.TWOTOUCH;
		if(leftPressed)
			return BehaviourReason.LEFTTOUCH;
		return BehaviourReason.RIGHTTOUCH;
	}
	@Override
	public void action() {

		Message msg = new Message(Monitor.BehaviorMonitor,
				BehaviourIdentifier.TouchSensorBehaviour,
				sensorsPressed());
		Communicator.instance().send(msg);
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
		leftPressed = left.isPressed();
		rightPressed = right.isPressed();
		return leftPressed || rightPressed;
	}

	
}