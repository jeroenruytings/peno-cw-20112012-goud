package Robot;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.util.Stopwatch;

public class CommandoListener implements Runnable {
	
	private RobotCommunicator communicator;
	DifferentialPilot pilot;
	SensorListener listener;
	public int getBlack() {
		return Black;
	}

	public void setBlack(int black) {
		Black = black;
	}

	public int getWhite() {
		return White;
	}

	public void setWhite(int white) {
		White = white;
	}

	public int getBrown() {
		return Brown;
	}

	public void setBrown(int brown) {
		Brown = brown;
	}

	int Black;
	int White;
	int Brown;
	private boolean totalCrash;
	
	public CommandoListener(SensorListener listener){
		
		communicator = RobotCommunicator.instance();
		pilot = new DifferentialPilot(54.5f, 54.75f, 156f, Motor.A, Motor.B, false);
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
				case 11:
					correctToMiddle();
					break;
				case 12:
					Sound.twoBeeps();
					restore();
					break;
				default:;
			}
		}
		
		}

//	private void correctFromCrash(){
//		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
//		
//		Motor.A.setSpeed(360);
//		Motor.B.setSpeed(360);
//		
//		pilot.travel(-10);
//		
//		while(!iswhite(listener.getLightValue())){
//			Motor.A.forward();
//			Motor.B.forward();
//		}
//			
//		pilot.stop();
//		
//		Motor.A.setSpeed(60);
//		Motor.B.setSpeed(60);
//		
//		Motor.A.resetTachoCount();
//		
//		while(iswhite(listener.getLightValue())){
//			Motor.A.forward();
//			Motor.B.forward();
//		}
//			
//		pilot.stop();
//		int lineWidth = Motor.A.getTachoCount();
//		
//		communicator.send(message);
//		
//	}
	
	private void correctToMiddle() {
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		
		Motor.A.setSpeed(360);
		Motor.A.setSpeed(360);

		correctToMiddleX();
		setHeadZero();
		if(!getTotalCrash())
			correctToMiddleY();
		communicator.send(message);
	}
	
	private void setHeadZero() {
	
		
			int currentPosition = listener.getHeadTacho();
			Motor.C.rotate(-currentPosition);
	}
		
		
	


	private void correctToMiddleX(){
		Stopwatch sw = new Stopwatch();
		sw.reset();
		while(!iswhite(listener.getLightValue())){
			Motor.A.backward();
			Motor.B.backward();
			if(sw.elapsed() > 5000){
				setTotalCrash(true);
				return;
			}
		}
		pilot.travel(40);
		pilot.rotate(-180, true);
		Motor.B.resetTachoCount();
		interruptToWhite();
		int tellerL = Motor.B.getTachoCount();
		
		Motor.B.resetTachoCount();
		pilot.rotate(90,true);
		interruptToBrown();
		
		pilot.rotate(180,true);
		interruptToWhite();
		int tellerR = Motor.B.getTachoCount();
		
		System.out.println("linkerteller: " + tellerL);
		System.out.println("Rechterteller: " + tellerR);
		
		
		int teller = (int) (0.33*tellerR);
		pilot.rotate(-360, true);
		interruptWhenDestinationReached(teller);
		
		pilot.travel(200);
	}
	
	
	//TODO kijk waarden na voor sonar, rotate en travel
	private void restore() {
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));

		Sound.twoBeeps();
		setHeadZero();
		pilot.travel(10);
		while (true) {
			if (listener.getSonarValue() < 20) {
				pilot.rotate(20);
				setHeadZero();
			}
			if (listener.getPushValue() == 1){
				pilot.travel(-10);
			}
			else{
				correctToMiddle();
				communicator.send(message);
				return;
			}
		}
		
		
		
//		
//		setHeadZero();
//		while(!iswhite(listener.getLightValue())){
//			Motor.A.forward();
//			Motor.B.forward();
//			if((listener.getPushValue()==1) || (listener.getSonarValue()<20)){
//				
//				pilot.rotate(20);
//				setHeadZero();
//			}
//		}
//		setHeadZero();
//		while(iswhite(listener.getLightValue())){
//			Motor.A.forward();
//			Motor.B.forward();
//		}
//		pilot.travel(10);
//		correctToMiddle();
	}

	private void setTotalCrash(boolean b) {
		listener.setTotalCrash(b);
		totalCrash = b;
	}
	
	private boolean getTotalCrash(){
		return totalCrash;
	}

	private void correctToMiddleY(){
		
		Motor.C.rotate(90);
		Message headMessage = new Message(Monitor.SensorMonitor, SensorIdentifier.HeadTacho, new SensorValue((byte)listener.getHeadTacho()));
		communicator.send(headMessage);
		int left = listener.getSonarValue();
		Motor.C.rotate(-90);
		headMessage = new Message(Monitor.SensorMonitor, SensorIdentifier.HeadTacho, new SensorValue((byte)listener.getHeadTacho()));
		communicator.send(headMessage);
		
		if(left>27){
			left(90);
			pilot.travel(400, true);
			interruptToWhite();
			left(185);
			pilot.travel(155);
			left(90);
			return;
		}
		
		Motor.C.rotate(-90);
		headMessage = new Message(Monitor.SensorMonitor, SensorIdentifier.HeadTacho, new SensorValue((byte)listener.getHeadTacho()));
		communicator.send(headMessage);
		int right = listener.getSonarValue();
		Motor.C.rotate(90);
		headMessage = new Message(Monitor.SensorMonitor, SensorIdentifier.HeadTacho, new SensorValue((byte)listener.getHeadTacho()));
		communicator.send(headMessage);
		
		if(right>27){
			right(90);
			pilot.travel(400, true);
			interruptToWhite();
			right(185);
			pilot.travel(155);
			right(90);
			return;
		}
		else{
			right = right - 6;
			int diff = (right - left)/2;
			if(diff<0){
				left(90);
				pilot.travel(-diff);
				right(90);
			}
			else if(diff>0){
				right(90);
				pilot.travel(diff);
				left(90);
			}
		}
	}

	private void interruptWhenDestinationReached(int teller) {
		Motor.B.resetTachoCount();
		while(pilot.isMoving()){
			if(Motor.B.getTachoCount()<(-teller)){
				pilot.stop();
			}
		}
	}

	private void interruptToBrown() {
		while(pilot.isMoving()){
			if(isbrown(listener.getLightValue())){
				pilot.stop();
			}
		}
	}

	private void interruptToWhite() {
		while(pilot.isMoving()){
			if(iswhite(listener.getLightValue())){
				pilot.stop();
			}
		}
	}

	private void turnHeadLeft(int argument){
		
		Motor.C.rotate(argument);
		System.out.println("L: " + Motor.C.getTachoCount());
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte) listener.getSonarValue()));
		communicator.send(message);
		message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
	}

	private void turnHeadRight(int argument){
		
		Motor.C.rotate(-argument);
		System.out.println("R: " + Motor.C.getTachoCount());
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte) listener.getSonarValue()));
		communicator.send(message);
		message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
		
	}


	private void calibrateBrown() {
		LCD.clear();
		LCD.drawString("BRUIN (enter)", 0, 0);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		setBrown(listener.getLightValue());
		communicator.send(message);
		System.out.println("OK");
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
		setWhite(listener.getLightValue());
		communicator.send(message);
		System.out.println("OK");
	}

	private void calibrateBlack() {
		LCD.clear();
		LCD.drawString("BLACK (enter)", 0, 0);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		
		lejos.nxt.Button.ENTER.waitForPressAndRelease();
		
		setBlack(listener.getLightValue());
		communicator.send(message);
		System.out.println("OK");
	}
	
	public boolean iswhite(int value)
	{
		return value < getWhite() + (getBrown() - getWhite()) / 2;
	}

	public boolean isblack(int value)
	{
		return value > getBlack() - (getBlack() - getWhite()) / 2;
	}

	public boolean isbrown(int v)
	{
		return !isblack(v) && !iswhite(v);
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
		case 11:
			return new Commando(Action.CORRECT, comm-(k*1000), "");
		//nog extra commando's invoegen;
		default:
			return null;
		}
		
	}
	
	public void readBarcode(){
		Motor.A.resetTachoCount();
//		Button.ENTER.waitForPressAndRelease();
		pilot.travel(-150);
		Motor.A.resetTachoCount();
		pilot.setSpeed(180);
//		Button.ENTER.waitForPressAndRelease();
		pilot.travel(280);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
		Motor.A.resetTachoCount();
		pilot.setSpeed(360);
//		Button.ENTER.waitForPressAndRelease();
		pilot.travel(-130);
		Motor.A.resetTachoCount();
//		Button.ENTER.waitForPressAndRelease();
	}
	
private void right(int i) {
	pilot.rotate(i);
}

private void left(int i) {
	pilot.rotate(-i);
}

private void backward(int i) {
	pilot.travel(-i);
}

private void forward(int i) {
	pilot.travel(i);
	
}

private void stop() {
	Motor.A.stop();
	Motor.B.stop();
}

}
