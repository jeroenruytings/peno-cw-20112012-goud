package pacmansystem.ai.robot.simulatedRobot.Robot;


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
	
	public CommandoListener(SensorListener listener){
		
		communicator = RobotCommunicator.instance();
		pilot = new DifferentialPilot(54.5f, 54.75f, 148.35f, Motor.A, Motor.B, false);
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
				default:;
			}
		}
		
		}

	private void correctToMiddle() {
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		pilot.rotate(180);
		Motor.A.setSpeed(360);
		Motor.B.setSpeed(360);
		
		while(!iswhite(listener.getLightValue())){
			Motor.A.forward();
			Motor.B.forward();
		}
			
		pilot.stop();
		
		Motor.A.setSpeed(60);
		Motor.B.setSpeed(60);
		
		Motor.A.resetTachoCount();
		
		while(iswhite(listener.getLightValue())){
			Motor.A.forward();
			Motor.B.forward();
		}
			
		pilot.stop();
		int lineWidth = Motor.A.getTachoCount();
		
		System.out.println("breedte = " + lineWidth);
		Button.ENTER.waitForPressAndRelease();
		communicator.send(message);
		
		
		
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
		Button.ENTER.waitForPressAndRelease();
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
		Button.ENTER.waitForPressAndRelease();
		setWhite(listener.getLightValue());
		communicator.send(message);
		System.out.println("OK");
	}

	private void calibrateBlack() {
		LCD.clear();
		LCD.drawString("BLACK (enter)", 0, 0);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		Button.ENTER.waitForPressAndRelease();
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
		pilot.travel(-160);
		Motor.A.resetTachoCount();
//		Button.ENTER.waitForPressAndRelease();
		pilot.travel(320);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
		Motor.A.resetTachoCount();
//		Button.ENTER.waitForPressAndRelease();
		pilot.travel(-160);
		Motor.A.resetTachoCount();
//		Button.ENTER.waitForPressAndRelease();
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
