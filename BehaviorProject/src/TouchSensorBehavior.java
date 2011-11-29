
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;


public class TouchSensorBehavior extends LeoBehavior {
	
	TouchSensor left = new TouchSensor(SensorPort.S4);
	TouchSensor right = new TouchSensor(SensorPort.S1);
	boolean suppressed = true;
	boolean rightPressed = false;
	boolean leftPressed = false;

	@Override
	public void action() {
		if (leftPressed && rightPressed)
			Communicator.instance().send(
					new Message(Monitor.BehaviorMonitor,
							BehaviourIdentifier.TouchSensorBehaviour,
							BehaviourReason.TWOTOUCH));
		else if (leftPressed)
			Communicator.instance().send(
					new Message(Monitor.BehaviorMonitor,
							BehaviourIdentifier.TouchSensorBehaviour,
							BehaviourReason.LEFTTOUCH));
		else if (rightPressed)
			Communicator.instance().send(
					new Message(Monitor.BehaviorMonitor,
							BehaviourIdentifier.TouchSensorBehaviour,
							BehaviourReason.RIGHTTOUCH));
		
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