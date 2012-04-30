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
	
	public CommandoListener(SensorListener listener,RobotCommunicator comm){
		
		communicator =comm;
		pilot = new DifferentialPilot(0);
		this.listener = listener;
	}
	

	@Override
	public void run() {
		while (true){
			executeCommando(communicator.receiveCommando());
		}
		
	}
	
	private void executeCommando(Commando receivedCommando) {
		
		if (receivedCommando == null){
			
			return;
		}
		else{
			switch (receivedCommando.getAction()){
				case STOP:
					stop();
					break;
				case FORWARD:
					forward(receivedCommando.getArgument());
					break;
				case BACKWARD:
					backward(receivedCommando.getArgument());
					break;
				case LEFT:
					left(receivedCommando.getArgument());
					break;
				case RIGHT:
					right(receivedCommando.getArgument());
					break;
				case CALIBRATEBLACK:
					calibrateBlack();
					break;
				case CALIBRATEWHITE:
					calibrateWhite();
					break;
				case CALIBRATEBROWN:
					calibrateBrown();
					break;
				case READBARCODE:
					readBarcode();
					break;
				case HEADRIGHT:
					turnHeadRight(receivedCommando.getArgument());
					break;
				case HEADLEFT:
					turnHeadLeft(receivedCommando.getArgument());
					break;
				case CORRECT:
					correctToMiddle();
					break;
				default:;
			}
		}
		
		}

	private void correctToMiddle() {
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		pilot.rotate(180);
		pilot.setSpeed(360);
		
		while(!iswhite(listener.getLightValue())){
			pilot.forward();
		}
			
		pilot.stop();
		pilot.setSpeed(60);
		pilot.resetTachoCount();
		
		while(iswhite(listener.getLightValue())){
			pilot.forward();
		}
			
		pilot.stop();
		pilot.waitForPress();
		communicator.send(message);
		
		
		
	}

	private void turnHeadLeft(int argument){
		
		pilot.getHead().rotate(argument);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte) listener.getSonarValue()));
		communicator.send(message);
		message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
	}

	private void turnHeadRight(int argument){
		
		pilot.getHead().rotate(-argument);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte) listener.getSonarValue()));
		communicator.send(message);
		message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
		
	}


	private void calibrateBrown() {
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		pilot.waitForPress();
		setBrown(listener.getLightValue());
		communicator.send(message);
		System.out.println("OK");
	}

	private void calibrateWhite() {
	//	Button.ENTER.waitForPressAndRelease();
	//	System.out.println("Wit:" + listener.getLightValue()*4);
	//	Button.ENTER.waitForPressAndRelease();
	//	Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.LightSensor, new SensorValue((byte)listener.getLightValue()));
	//	communicator.send(message);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
	//	System.out.println("send message enter");
		pilot.waitForPress();
		setWhite(listener.getLightValue());
		communicator.send(message);
		System.out.println("OK");
	}

	private void calibrateBlack() {
		//TODO:FIX
//		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
//		pilot.waitForPress();
//		setBlack(listener.getLightValue());
//		communicator.send(message);
//		System.out.println("OK");
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
		pilot.resetTachoCount();
		pilot.travel(-160);
		pilot.resetTachoCount();
		pilot.travel(320);
		Message message = new Message(Monitor.SensorMonitor, SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
		pilot.resetTachoCount();
		pilot.travel(-160);
		pilot.resetTachoCount();
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
	pilot.stop();
}

}
