package pacmansystem.ai.robot.simulatedRobo.Robot;

import pacmansystem.ai.robot.simulatedRobo.ticking.Tickable;
import pacmansystem.ai.robot.simulatedRobo.ticking.Ticker;

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
		sendValue(robot.getPilot().getTachoCount()/4, SensorIdentifier.TachoCount);

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
			//System.out.println("send"+sonar.getDistance());
			sendValue(sonar.getDistance(), SensorIdentifier.UltrasonicSensor);
		}
	}

	private void sendLight()
	{
		if(forcelight)
			{
				sendValue(lightValue, SensorIdentifier.LightSensor);
			return;
			}
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


	@Override
	public int importance()
	{
		// TODO Auto-generated method stub
		return 9;
	}

}
