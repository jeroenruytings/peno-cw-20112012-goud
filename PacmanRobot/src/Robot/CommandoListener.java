package Robot;

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
		default:;
		}
		
	}

	public static Commando decodeCommando(int comm){
		switch(comm){
		case 0:
			return new Commando(Action.STOP,"");
		case 1:
			return new Commando(Action.FORWARD,"");
		//nog extra commando's invoegen;
		default:
			return null;
		}
		
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
