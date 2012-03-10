package Robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.proposal.DifferentialPilot;

public class CommandoListener implements Runnable {
	
	private RobotCommunicator communicator;
	DifferentialPilot pilot;
	SensorListener listener;
	
	public CommandoListener(SensorListener listener){
		
		communicator = RobotCommunicator.instance();
		pilot = new DifferentialPilot(54.25f, 54.75f, 148.35f, Motor.A, Motor.B, false);
		this.listener = listener;
	}
	
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
					readBarcode();
					break;
				case 9:
					turnHeadRight(receivedCommando.getArgument());
					break;
				case 10:
					turnHeadLeft(receivedCommando.getArgument());
					break;
				default:;
			}
		}
		
		}


	private void turnHeadLeft(int argument){
		
		Motor.C.rotate(argument);
		System.out.println("L: " + Motor.C.getTachoCount());
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte) listener.getSonarValue()));
		communicator.send(message);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			System.out.println("ii");
//		}
		message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
	}

	private void turnHeadRight(int argument){
		
		Motor.C.rotate(-argument);
		System.out.println("R: " + Motor.C.getTachoCount());
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte) listener.getSonarValue()));
		communicator.send(message);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			System.out.println("iiii");
//		}
		message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
		
	}


	private void calibrateBrown() {
		LCD.clear();
		LCD.drawString("BRUIN (enter)", 0, 0);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		System.out.println("Bruin:" + listener.getLightValue());
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		communicator.send(message);
	}

	private void calibrateWhite() {
		LCD.clear();
		LCD.drawString("WIT (enter)", 0, 0);
	//	Button.ENTER.waitForPressAndRelease();
	//	System.out.println("Wit:" + listener.getLightValue()*4);
	//	Button.ENTER.waitForPressAndRelease();
	//	Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.LightSensor, new SensorValue((byte)listener.getLightValue()));
	//	communicator.send(message);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
	//	System.out.println("send message enter");
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		System.out.println("Wit:" + listener.getLightValue());
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		communicator.send(message);
	}

	private void calibrateBlack() {
		LCD.clear();
		LCD.drawString("BLACK (enter)", 0, 0);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		System.out.println("Zwart:" + listener.getLightValue());
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
		case 8:
			return new Commando(Action.READBARCODE,comm-(k*1000),"");
		case 9:
			return new Commando(Action.HEADRIGHT,comm-(k*1000), "");
		case 10:
			return new Commando(Action.HEADLEFT, comm-(k*1000), "");
		//nog extra commando's invoegen;
		default:
			return null;
		}
		
	}
	
	public void readBarcode(){
		pilot.travel(-160);
		pilot.travel(320);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
		pilot.travel(-160);
	}
	
private void right(int i) {
	
	System.out.println("distance = " + i);
	pilot.rotate(i);
}

private void left(int i) {
	
	System.out.println("distance = " + i);
	pilot.rotate(-i);
}

private void backward(int i) {
	
	System.out.println("distance = " + i);
	pilot.travel(-i);
}

private void forward(int i) {
	
	System.out.println("distance = " + i);
	pilot.travel(i);
	
}

private void stop() {
	Motor.A.stop();
	Motor.B.stop();
}

}
