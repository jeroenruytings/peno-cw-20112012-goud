

import Robot.CommandoListener;
import Robot.RobotCommunicator;
import Robot.SensorListener;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;


public class GhostController {
	
	private static DifferentialPilot p = new DifferentialPilot(54.25f, 54.75f,
			161.6f, Motor.A, Motor.B, false);
	//Poort C = kop
	
	public static void main(String[] args) {
		
		//start Threads

		SensorListener listener = new SensorListener();
		System.out.println("listener aanmaken");
		Thread listen = new Thread(listener);
		System.out.println("listener opstarten");
		listen.start();
		
		CommandoListener executer = new CommandoListener(listener);
		Thread execute = new Thread(executer);
		execute.start();
		System.out.println("opgestart");
		
		while (true);
		
		
//		boolean finished = false;
//		while(!finished){
//			System.out.println("Controller" + rec.getCurrentCommand());
//			switch (rec.getCurrentCommand()){
//			case 0:
//				stop();
//				break;
//			case 1:
//				forward();
//				break;
//			case 2:
//				backward();
//				break;
//			case 3:
//				left();
//				break;
//			case 4:
//				right();
//				break;
//			default:;
//		}
		}
		
		
//	}

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
	
	private static void driveForward(int distance){
		p.travel(distance);
	}
	
	private static void driveBackward(int distance){
		p.backward();
		p.travel(distance);
		p.forward();
	}
	
	private static void turnLeft(int degrees) {
		p.rotate(-degrees);
	}
	
	private static void turnRight(int degrees) {
		p.rotate(degrees);
	}
}
