package Robot;

import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class CommandoListener implements Runnable {
	
	private RobotCommunicator communicator = RobotCommunicator.instance();
	
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
					forward();
					break;
				case 2:
					backward();
					break;
				case 3:
					left();
					break;
				case 4:
					right();
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
				default:;
			}
		}
		
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
		switch(comm){
		case 0:
			return new Commando(Action.STOP,"");
		case 1:
			return new Commando(Action.FORWARD,"");
		case 2:
			return new Commando(Action.BACKWARD,"");
		case 3:
			return new Commando(Action.LEFT,"");
		case 4:
			return new Commando(Action.RIGHT,"");
		case 5:
			return new Commando(Action.CALIBRATEBLACK,"");
		case 6:
			return new Commando(Action.CALIBRATEWHITE, "");
		case 7:
			return new Commando(Action.CALIBRATEBROWN, "");
		//nog extra commando's invoegen;
		default:
			return null;
		}
		
	}
	
	public void readBarcode(){
		//TODO
	}
	
private static void right() {
	Motor.B.forward();
	Motor.A.backward();
}

private static void left() {
	Motor.B.backward();
	Motor.A.forward();
}

private static void backward() {
	Motor.B.backward();
	Motor.A.backward();
}

private static void forward() {
	Motor.B.forward();
	Motor.A.forward();
}

private static void stop() {
	Motor.A.stop();
	Motor.B.stop();
}

}
