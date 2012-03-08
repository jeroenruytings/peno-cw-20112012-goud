package Robot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.IRSeeker;


public class SensorListener implements Runnable {
	
	UltrasonicSensor sonar;
	LightSensor light;
	TouchSensor push;
	IRSeeker ir;
	Motor motor;
	Motor head;
	RobotCommunicator communicator;
	
	public SensorListener(){
		
		 sonar = new UltrasonicSensor(SensorPort.S3); //check
		 light = new LightSensor(SensorPort.S1);	//check
		 push = new TouchSensor(SensorPort.S4);	//check
		 ir = new IRSeeker(SensorPort.S2);	//check
		 motor = new Motor(MotorPort.A);
		 head = new Motor(MotorPort.C);
		communicator = RobotCommunicator.instance();
		
	}
	
	int sonarValue;
	int lightValue;
	int pushValue; 
	int irValue; 
	int irDirection;
	int tachoCount; 
	int headTacho;


	
	
	public void start(){
	ir.setAddress(0x8);
	sonarValue = sonar.getDistance(); 
	lightValue = light.getLightValue();
	pushValue = 0;
	irDirection = ir.getDirection();	
	irValue = ir.getSensorValue(irDirection); 
	tachoCount = motor.getTachoCount();
	headTacho = head.getTachoCount();
	
	Thread listener = new Thread(this);
    listener.start();
	}

	@Override
	public void run() {
		
		while (true){
			
		//send push value
			if(pushValue != getPushValue()){
				pushValue = getPushValue();
		sendValue(pushValue, SensorIdentifier.PushSensor);
			}
		Thread.yield();
				
		//send light value
			if(lightValue != getLightValue()){
				lightValue = getLightValue();
		sendValue(lightValue, SensorIdentifier.LightSensor);
			}
		Thread.yield();
		
		// send sonar value
			if(sonarValue != getSonarValue()){
				sonarValue = getSonarValue();
		sendValue(sonarValue, SensorIdentifier.UltrasonicSensor);
			}
		Thread.yield();
		
		// send direction ir	
			if(irDirection != getIrDirection()){
				irDirection = getIrDirection();
		sendValue(irDirection, SensorIdentifier.DirectionIrSensor);
			}
		Thread.yield();
		
//		// send sonar value
//			if(irValue != getIrValue()){
//				irValue = getIrValue();
//		sendValue(irValue, SensorIdentifier.ValueIrSensor);
//			}
//		Thread.yield();
		
		//send Tachocount
			if(tachoCount != getTachoCount()){
				tachoCount = getTachoCount();
		sendValue(tachoCount, SensorIdentifier.TachoCount);
			}
		Thread.yield();
		
//		//send HeadTachoCount
//			if(headTacho != getHeadTacho()){
//				headTacho = getHeadTacho();
//		sendValue(headTacho, SensorIdentifier.HeadTacho);
//			}
//			Thread.yield();
		
		
		}	
		
	}

//	private int getHeadTacho() {
//		System.out.println("head " + head.getTachoCount());
//		Button.ENTER.waitForPressAndRelease();
//		return head.getTachoCount();
//	}

	public int getTachoCount() {
		System.out.println("tacho");
		Button.ENTER.waitForPressAndRelease();
		return motor.getTachoCount();
	}

	public int getIrDirection() {
		
		return ir.getDirection();
	}

	public int getSonarValue() {
		sonar.ping();
		return sonar.getDistance();
	}

	public int getLightValue() {
		System.out.println("sonar");
	Button.ENTER.waitForPressAndRelease();
		return light.readValue();
	}

	public int getIrValue() {
		return ir.getSensorValue(getIrDirection());
	}

	private void sendValue(int Value, SensorIdentifier sensorID) {
		
        Message mes = new Message(Monitor.SensorMonitor, sensorID, new SensorValue((byte)Value));
        communicator.send(mes);	
	}

	public int getPushValue() {
		if (push.isPressed()){
			return 1;
		}
		else {
			return 0;
		}
	}

}
