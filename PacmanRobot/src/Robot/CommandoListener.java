package Robot;

import java.awt.Button;

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
		if (receivedCommando == null)
			return;
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
		case 6:
			calibrateWhite();
		case 7:
			calibrateBrown();
			
		default:;
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
		//nog extra commando's invoegen;
		default:
			return null;
		}
		
	}
	
	public void readBarcode(){
		//TODO
	}
	
//	boolean finished = false;
//	while(!finished){
//		System.out.println("Controller" + rec.getCurrentCommand());
//		switch (rec.getCurrentCommand()){
//		case 0:
//			stop();
//			break;
//		case 1:
//			forward();
//			break;
//		case 2:
//			backward();
//			break;
//		case 3:
//			left();
//			break;
//		case 4:
//			right();
//			break;
//		default:;
//	}

	
	
//}

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
