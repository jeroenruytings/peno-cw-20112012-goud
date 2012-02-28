import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.IRSeeker;


public class SensorListener implements Runnable {
	
	UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S3);
	LightSensor light = new LightSensor(SensorPort.S2);
	TouchSensor push = new TouchSensor(SensorPort.S1);
	IRSeeker ir = new IRSeeker(SensorPort.S4);
	
	
	public void start(){
	
	Thread listener = new Thread(this);
    listener.start();
	}

	@Override
	public void run() {
		
		while (true){
		
		//send push value
		int pushValue = getPushValue();
		sendValue(pushValue, SensorIdentifier.PushSensor);
				
		//send light value
		int lightValue = getLightValue();
		sendValue(lightValue, SensorIdentifier.LightSensor);
		
		// send sonar value
		int sonarValue = getSonarValue();
		sendValue(sonarValue, SensorIdentifier.UltrasonicSensor);
		
		// send direction ir
		int irDirection = getIrDirection();
		sendValue(sonarValue, SensorIdentifier.DirectionIrSensor);
		
		// send sonar value
		int irValue = getIrValue();
		sendValue(sonarValue, SensorIdentifier.ValueIrSensor);
		
		}	
		
	}

	private int getIrDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getSonarValue() {
		
		return sonar.getDistance();			
		
	}

	private int getLightValue() {
		return light.readValue();
	}

	private int getIrValue() {
		
		//TODO op moment standaardwaarde
		return ir.getSensorValue(5);
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
