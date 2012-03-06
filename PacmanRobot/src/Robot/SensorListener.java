package Robot;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.IRSeeker;


public class SensorListener implements Runnable {
	
	UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S3); //check
	LightSensor light = new LightSensor(SensorPort.S1);	//check
	TouchSensor push = new TouchSensor(SensorPort.S4);	//check
	IRSeeker ir = new IRSeeker(SensorPort.S2);	//check
	Motor motor = new Motor(MotorPort.A);
	
	int sonarValue;
	int lightValue;
	int pushValue; 
	int irValue; 
	int irDirection;
	int tachoCount; 


	
	
	public void start(){
	ir.setAddress(0x8);
	sonarValue = sonar.getDistance(); 
	lightValue = light.getLightValue();
	pushValue = 0;
	irDirection = ir.getDirection();	
	irValue = ir.getSensorValue(irDirection); 
	tachoCount = new Motor(MotorPort.A).getTachoCount();
	
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
		
		// send sonar value
			if(irValue != getIrValue()){
				irValue = getIrValue();
		sendValue(irValue, SensorIdentifier.ValueIrSensor);
			}
		Thread.yield();
		
		//sent Tachocount
			if(tachoCount != getTachoCount()){
				tachoCount = getTachoCount();
		sendValue(tachoCount, SensorIdentifier.TachoCount);
			}
		Thread.yield();
		
		
		}	
		
	}

	public int getTachoCount() {
		return motor.getTachoCount();
	}

	public int getIrDirection() {
		
		return ir.getDirection();
	}

	public int getSonarValue() {	
		return sonar.getDistance();
	}

	public int getLightValue() {
		return light.readValue();
	}

	public int getIrValue() {
		return ir.getSensorValue(getIrDirection());
	}

	private void sendValue(int Value, SensorIdentifier sensorID) {
		
        Message mes = new Message(Monitor.SensorMonitor, sensorID, new SensorValue((byte)Value));
        RobotCommunicator.instance().send(mes);	
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
