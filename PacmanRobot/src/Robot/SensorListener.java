package Robot;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;


public class SensorListener implements Runnable {
	
	UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S3); //check
	LightSensor light = new LightSensor(SensorPort.S1);	//check
	TouchSensor push = new TouchSensor(SensorPort.S4);	//check
	IRSeekerV2 ir = new IRSeekerV2(SensorPort.S2);	//check
	Motor motor = new Motor(MotorPort.A);
	Motor head = new Motor(MotorPort.C);
	RobotCommunicator communicator;
	
	public SensorListener(){
		
		communicator = RobotCommunicator.instance();
		
	}
	
	private int sonarValue;
	private int lightValue;
	private int pushValue; 
	private int irValue; 
	private int irDirection;
	private int tachoCount;
	private int headTacho;
	


	
	
	public void start(){
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
				System.out.println(irDirection);
		sendValue(irDirection, SensorIdentifier.DirectionIrSensor);
			}
		Thread.yield();
		
		//send head tacho
		if(headTacho != getHeadTacho()){
			headTacho = getHeadTacho();
			System.out.println(headTacho);
	sendValue(headTacho, SensorIdentifier.HeadTacho);
		}
	Thread.yield();
		
//		// send sonar value
//			if(irValue != getIrValue()){
//				irValue = getIrValue();
//		sendValue(irValue, SensorIdentifier.ValueIrSensor);
//			}
//		Thread.yield();
		
		//sent Tachocount
			if(tachoCount != getTachoCount() && getTachoCount()>=0){
				tachoCount = getTachoCount();
		sendValue(tachoCount, SensorIdentifier.TachoCount);
			}
		Thread.yield();
		
		
		}	
		
	}

	private int getHeadTacho() {
		return head.getTachoCount();
	}

	public int getTachoCount() {
		return motor.getTachoCount()/4;
	}

	public int getIrDirection() {
		
		return ir.getDirection();
	}

	public int getSonarValue() {
		sonar.ping();
		return sonar.getDistance();
	}

	public int getLightValue() {
		return (SensorPort.S1.readRawValue()/4);
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
