package Robot;

import Robot.IRSeekerV2.Mode;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;


public class SensorListener implements Runnable {
	
	UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S3); //check
	LightSensor light = new LightSensor(SensorPort.S1);	//check
	TouchSensor push = new TouchSensor(SensorPort.S4);	//check
	IRSeekerV2 ir = new IRSeekerV2(SensorPort.S2, Mode.AC);	//check
	
	
	public void start(){
	
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
		
//		// send direction ir
//		int irDirection = getIrDirection();
//		sendValue(irDirection, SensorIdentifier.DirectionIrSensor);
//		
//		// send sonar value
//		int irValue = getIrValue();
//		sendValue(irValue, SensorIdentifier.ValueIrSensor);
		
		}	
		
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
