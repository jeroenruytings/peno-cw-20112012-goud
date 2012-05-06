package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.SIMINFO;

public class CommandoListener implements Runnable
{

	private RobotCommunicator communicator;
	Robot robot;
	SensorListener listener;

	public int getBlack()
	{
		return Black;
	}

	public void setBlack(int black)
	{
		Black = black;
	}

	public int getWhite()
	{
		return White;
	}

	public void setWhite(int white)
	{
		White = white;
	}

	public int getBrown()
	{
		return Brown;
	}

	public void setBrown(int brown)
	{
		Brown = brown;
	}

	int Black;
	int White;
	int Brown;

	public CommandoListener(SensorListener listener, RobotCommunicator comm,
			Robot robot)
	{
		if(listener==null||comm==null||robot==null)
			throw new IllegalArgumentException("dont give null@ a listener communicator or robot");
		communicator = comm;
		this.robot = robot;
		this.listener = listener;
	}

	@Override
	public void run()
	{
		while (true) {
			executeCommando(communicator.receiveCommando());
		}

	}

	private void executeCommando(Commando receivedCommando)
	{

		if (receivedCommando == null) {

			return;
		} else {
			System.out.println(receivedCommando.getAction().name());
			switch (receivedCommando.getAction())
			{
			
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
			default:
				;
			}
		}

	}

	private void correctToMiddle()
	{
		Message message = new Message(Monitor.SensorMonitor,
				SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
//		robot.getPilot().rotate(180);
//		robot.getPilot().setSpeed(360);
//
//		while (!iswhite(listener.getLightValue())) {
//			robot.getPilot().forward();
//		}
//
//		robot.getPilot().stop();
//		robot.getPilot().setSpeed(60);
//		robot.getPilot().resetTachoCount();
//
//		while (iswhite(listener.getLightValue())) {
//			robot.getPilot().forward();
//		}
//
//		robot.getPilot().stop();
//		robot.waitForPress();
		communicator.send(message);

	}

	private void turnHeadLeft(int argument)
	{
		robot.getHead().rotate(argument);
		Message message = new Message(Monitor.SensorMonitor,
				SensorIdentifier.UltrasonicSensor, new SensorValue(
						(byte) listener.getSonarValue()));
		communicator.send(message);
		message = new Message(Monitor.SensorMonitor,
				SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
	}

	private void turnHeadRight(int argument)
	{

		System.out.println("turning head right");
		robot.getHead().rotate(-argument);
		Message message = new Message(Monitor.SensorMonitor,
				SensorIdentifier.UltrasonicSensor, new SensorValue(
						(byte) listener.getSonarValue()));
		communicator.send(message);
		message = new Message(Monitor.SensorMonitor,
				SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);

	}

	private void calibrateBrown()
	{

		 Message message = new Message(Monitor.SensorMonitor,
		 SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		listener.forceLight(SIMINFO.BROWN/4);
		 setBlack(listener.getLightValue());
		communicator.send(message);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		listener.unforceLight();
	}

	private void calibrateWhite()
	{

		 Message message = new Message(Monitor.SensorMonitor,
		 SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		listener.forceLight(SIMINFO.WHITE/4);
		 setBlack(listener.getLightValue());
		communicator.send(message);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		listener.unforceLight();
	}

	private void calibrateBlack()
	{
		
	
		 Message message = new Message(Monitor.SensorMonitor,
		 SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		listener.forceLight(SIMINFO.BLACK/4);
		 setBlack(listener.getLightValue());
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		communicator.send(message);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		listener.unforceLight();
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

	public static Commando decodeCommando(int comm)
	{
		int k = comm / 1000;
		switch (k)
		{
		case 0:
			return new Commando(Action.STOP, comm - (k * 1000), "");
		case 1:
			return new Commando(Action.FORWARD, comm - (k * 1000), "");
		case 2:
			return new Commando(Action.BACKWARD, comm - (k * 1000), "");
		case 3:
			return new Commando(Action.LEFT, comm - (k * 1000), "");
		case 4:
			return new Commando(Action.RIGHT, comm - (k * 1000), "");
		case 5:
			return new Commando(Action.CALIBRATEBLACK, comm - (k * 1000), "");
		case 6:
			return new Commando(Action.CALIBRATEWHITE, comm - (k * 1000), "");
		case 7:
			return new Commando(Action.CALIBRATEBROWN, comm - (k * 1000), "");
		case 8:
			return new Commando(Action.READBARCODE, comm - (k * 1000), "");
		case 9:
			return new Commando(Action.HEADRIGHT, comm - (k * 1000), "");
		case 10:
			return new Commando(Action.HEADLEFT, comm - (k * 1000), "");
		case 11:
			return new Commando(Action.CORRECT, comm - (k * 1000), "");
			// nog extra commando's invoegen;
		default:
			return null;
		}

	}

	public void readBarcode()
	{
		robot.getPilot().resetTachoCount();
		robot.getPilot().travel(-160);
		robot.getPilot().resetTachoCount();
		robot.getPilot().travel(320);
		Message message = new Message(Monitor.SensorMonitor,
				SensorIdentifier.ButtonPressed, new SensorValue((byte) 1));
		communicator.send(message);
		robot.getPilot().resetTachoCount();
		robot.getPilot().travel(-160);
		robot.getPilot().resetTachoCount();
	}

	private void right(int i)
	{
		robot.getPilot().rotate(i);
	}

	private void left(int i)
	{

		robot.getPilot().rotate(-i);
	}

	private void backward(int i)
	{

		robot.getPilot().travel(-i);
	}

	private void forward(int i)
	{

		robot.getPilot().travel(i);

	}

	private void stop()
	{
		robot.getPilot().stop();
	}

}
