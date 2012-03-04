package Robot;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;

public class CommandoListener implements Runnable {
	
	private RobotCommunicator communicator = RobotCommunicator.instance();
	DifferentialPilot pilot = new DifferentialPilot(0, 0, 0, null, null, false);
	
	public void start(){
		Thread listener = new Thread(this);
	    listener.start();
	}

	@Override
	public void run() {
		while (true){
			executeCommando(communicator.receiveCommando());
		}
		
	}
	
	private void executeCommando(Commando receivedCommando) {
		LCD.clear();
		LCD.drawString("EXECUTE COMMAND", 0, 0);
		
		if (receivedCommando == null){
			LCD.drawString("NULL", 0, 2);
			return;
		}
		else{
			switch (receivedCommando.getAction().ordinal()){
				case 0:
					stop();
					break;
				case 1:
					forward(receivedCommando.getArgument());
					break;
				case 2:
					backward(receivedCommando.getArgument());
					break;
				case 3:
					left(receivedCommando.getArgument());
					break;
				case 4:
					right(receivedCommando.getArgument());
					break;
				case 5:
					calibrateBlack();
					break;
				case 6:
					calibrateWhite();
					break;
				case 7:
					calibrateBrown();
					break;
				case 8:
					break;
				case 9:
					goGetLightSensorValue();
				default:;
			}
		}
		
		}

	private void goGetLightSensorValue() {
		
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.LightSensor, new SensorValue((byte) 1)); //XXX: welke value??
		communicator.send(message);
		
	}

	private void calibrateBrown() {
		LCD.clear();
		LCD.drawString("BRUIN (enter)", 0, 0);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		communicator.send(message);
	}

	private void calibrateWhite() {
		LCD.clear();
		LCD.drawString("WIT (enter)", 0, 0);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		communicator.send(message);
	}

	private void calibrateBlack() {
		LCD.clear();
		LCD.drawString("BLACK (enter)", 0, 0);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		communicator.send(message);
	}

	public static Commando decodeCommando(int comm){
		LCD.clear();
		System.out.println("IN decode!");
		int k = comm/1000;
		switch(k){
		case 0:
			return new Commando(Action.STOP,comm-(k*1000),"");
		case 1:
			return new Commando(Action.FORWARD,comm-(k*1000), "");
		case 2: 
			return new Commando(Action.BACKWARD,comm-(k*1000),"");
		case 3:
			return new Commando(Action.LEFT,comm-(k*1000),"");
		case 4:
			return new Commando(Action.RIGHT,comm-(k*1000),"");
		case 5:
			return new Commando(Action.CALIBRATEBLACK,comm-(k*1000),"");
		case 6:
			return new Commando(Action.CALIBRATEWHITE,comm-(k*1000),"");
		case 7:
			return new Commando(Action.CALIBRATEBROWN,comm-(k*1000),"");
		//nog extra commando's invoegen;
		default:
			return null;
		}
		
	}
	
	public void readBarcode(){
		//TODO
	}
	
private static void right(int i) {
	
	System.out.println("distance = " + i);
	Motor.B.forward();
	Motor.A.backward();
}

private static void left(int i) {
	
	System.out.println("distance = " + i);
	Motor.B.backward();
	Motor.A.forward();
}

private static void backward(int i) {
	
	System.out.println("distance = " + i);
	Motor.B.backward();
	Motor.A.backward();
}

private static void forward(int i) {
	
	System.out.println("distance = " + i);
	Motor.B.forward();
	Motor.A.forward();
}

private static void stop() {
	Motor.A.stop();
	Motor.B.stop();
}

}
