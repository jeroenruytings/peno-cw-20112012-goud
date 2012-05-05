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
	private boolean forcelight;
	private int lightValue;
	private Robot robot;

	public SensorListener(RobotCommunicator comm,UltrasonicSensor sensor,LightSensor lights,TouchSensor touch,IRSeekerV2 irseekr,Robot robot)
	{
		this.communicator = comm;
		this.sonar=sensor;
		this.light=lights;
		this.push=touch;
		this.ir=irseekr;
		this.robot=robot;
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
		sendValue(robot.getHead().getTacho(), SensorIdentifier.HeadTacho);
		
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
		if (sonar.hasChanged()) {
			sendValue(sonar.getDistance(), SensorIdentifier.UltrasonicSensor);
		}
	}

	private void sendLight()
	{
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
		if(forcelight)
			return lightValue;
		return light.getLightValue();
	}


	public int getSonarValue()
	{
		return sonar.getDistance();
	}


	public void forceLight(int light)
	{
		forcelight=true;
		lightValue=light;
	}


	public void unforceLight()
	{
		forcelight=false;
		
	}

}
