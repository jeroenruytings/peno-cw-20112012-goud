import lejos.nxt.Motor;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;


public class SonarBehavior extends LeoBehavior{
	
	boolean suppressed = true;
	UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S2);
	
	
	@Override
	public void action() {
		suppressed = false;
		Motor.C.rotate(-90);
		Delay.msDelay(50);
		int distance = sonar.getDistance();
		while(!suppressed && Motor.C.isMoving())
			Thread.yield();
		Motor.C.rotate(180);
		while(!suppressed && Motor.C.isMoving())
			Thread.yield();
		Delay.msDelay(50);
		if (distance > sonar.getDistance())
			pilot.travelArc(200, -250);
		else if(distance < sonar.getDistance())
			pilot.travelArc(-200, 250);
		else pilot.travel(-200);
		while(!suppressed && pilot.isMoving())
			Thread.yield();
		Motor.C.rotate(-90);
		while(!suppressed && Motor.C.isMoving())
			Thread.yield();
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;		
	}

	@Override
	public boolean takeControl() {
		return (sonar.getDistance() < 20);
	}

	
}
