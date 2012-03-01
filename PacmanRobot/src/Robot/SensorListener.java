package Robot;

import Robot.IRSeekerV2.Mode;
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

	
	
	public void start(){
	ir.setAddress(0x8);
	Thread listener = new Thread(this);
    listener.start();
	}

	@Override
	public void run() {
		
		while (true){
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		//send push value
		int pushValue = getPushValue();
		sendValue(pushValue, SensorIdentifier.PushSensor);
		Thread.yield();
				
		//send light value
		int lightValue = getLightValue();
		sendValue(lightValue, SensorIdentifier.LightSensor);
		Thread.yield();
		
		// send sonar value
		int sonarValue = getSonarValue();
		sendValue(sonarValue, SensorIdentifier.UltrasonicSensor);
		Thread.yield();
		
		// send direction ir
		int irDirection = getIrDirection();
		sendValue(irDirection, SensorIdentifier.DirectionIrSensor);
		Thread.yield();
		
		// send sonar value
		int irValue = getIrValue();
		sendValue(irValue, SensorIdentifier.ValueIrSensor);
		Thread.yield();
		
		//sent Tachocount
		int tachoCount = getTachoCount();
		sendValue(tachoCount, SensorIdentifier.TachoCount);
		Thread.yield();
		
		
		}	
		
	}

	private int getTachoCount() {
		return motor.getTachoCount();
	}

	private int getIrDirection() {
		
		return ir.getDirection();
	}

	private int getSonarValue() {
		
		return sonar.getDistance();			
		
	}

	private int getLightValue() {
		return light.readValue();
	}

	private int getIrValue() {
		return ir.getSensorValue(getIrDirection());
	}

	private void sendValue(int Value, SensorIdentifier sensorID) {
		
        Message mes = new Message(Monitor.SensorMonitor, sensorID, new SensorValue((byte)Value));
        RobotCommunicator.instance().send(mes);	
	}

	private int getPushValue() {
		if (push.isPressed()){
			return 1;
		}
		else {
			return 0;
		}
	}

}
