import lejos.nxt.SensorPort;


public class SensorListener implements Runnable {
	
	SensorPort push = SensorPort.S2;
	SensorPort ir = SensorPort.S4;
	SensorPort light = SensorPort.S1;
	SensorPort sonar = SensorPort.S3;
	
	
	public void start(){
	Thread listener = new Thread(this);
    listener.start();
	}

	@Override
	public void run() {
		
		//send push value
		int pushValue = getPushValue();
		sendValue(pushValue, SensorIdentifier.PushSensor);
		
		//send infra red value
		int irValue = getIrValue();
		sendValue(irValue, SensorIdentifier.UltrasonicSensor );
		
		//send light value
		int lightValue = getLightValue();
		sendValue(lightValue, SensorIdentifier.LightSensor);
		
		// send sonar value
		int sonarValue = getSonarValue();
		sendValue(sonarValue, SensorIdentifier.UltrasonicSensor);
		
		
		
	}

	private int getSonarValue() {
		return sonar.readRawValue();
	}

	private int getLightValue() {
		return light.readRawValue();
	}

	private int getIrValue() {
		return ir.readRawValue();
	}

	private void sendValue(int Value, SensorIdentifier sensorID) {
		
        Message mes = new Message(Monitor.SensorMonitor, sensorID, new SensorValue((byte)Value));
        RobotCommunicator.instance().send(mes);

		
	}

	private int getPushValue() {
		return push.readRawValue();
	}

}
