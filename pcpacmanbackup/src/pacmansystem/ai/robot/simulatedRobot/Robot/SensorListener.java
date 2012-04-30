package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.ticking.Tickable;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

public class SensorListener implements Tickable
{

	private final UltrasonicSensor sonar;  
	private final LightSensor light; 
	private final TouchSensor push;
	private final IRSeekerV2 ir ;
	RobotCommunicator communicator;

	public SensorListener(RobotCommunicator comm,UltrasonicSensor sensor,LightSensor lights,TouchSensor touch,IRSeekerV2 irseekr)
	{
		this.communicator = comm;
		this.sonar=sensor;
		this.light=lights;
		this.push=touch;
		this.ir=irseekr;
	}
	
	
	private void sendValue(int Value, SensorIdentifier sensorID)
	{

		Message mes = new Message(Monitor.SensorMonitor, sensorID,
				new SensorValue((byte) Value));
		communicator.send(mes);
	}



	@Override
	public void tick(Ticker ticker)
	{
		sendPush();
		sendLight();
		sendSonar();
		sendIR();

		sendHeadTacho();

		// // send sonar value
		// if(irValue != getIrValue()){
		// irValue = getIrValue();
		// sendValue(irValue, SensorIdentifier.ValueIrSensor);
		// }
		// Thread.yield();

		sendTacho();

	}

	private void sendTacho()
	{
		
//		if (tachoCount != getTachoCount() && getTachoCount() >= 0) {
//			tachoCount = getTachoCount();
//			sendValue(tachoCount, SensorIdentifier.TachoCount);
//		}
	}

	private void sendHeadTacho()
	{
//		if (headTacho != getHeadTacho()) {
//			headTacho = getHeadTacho();
//			System.out.println(headTacho);
//			sendValue(headTacho, SensorIdentifier.HeadTacho);
//		}
	}

	private void sendIR()
	{
//		if (irDirection != getIrDirection()) {
//			irDirection = getIrDirection();
//			System.out.println(irDirection);
//			sendValue(irDirection, SensorIdentifier.DirectionIrSensor);
//		}
	}

	private void sendSonar()
	{
//		if (sonarValue != getSonarValue()) {
//			sonarValue = getSonarValue();
//			sendValue(sonarValue, SensorIdentifier.UltrasonicSensor);
//		}
	}

	private void sendLight()
	{
		if(!light.hasChanged())
			return;
	sendValue(light.getLightValue(), SensorIdentifier.LightSensor);
		
	}

	private void sendPush()
	{
		
//		if (pushValue != getPushValue()) {
//			pushValue = getPushValue();
//			sendValue(pushValue, SensorIdentifier.PushSensor);
//		}
	}


	public int getLightValue()
	{
		return light.getLightValue();
	}


	public int getSonarValue()
	{
		return sonar.getDistance();
	}

}
