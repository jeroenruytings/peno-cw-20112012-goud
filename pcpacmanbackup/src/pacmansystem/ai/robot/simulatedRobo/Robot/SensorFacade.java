package pacmansystem.ai.robot.simulatedRobo.Robot;

public interface SensorFacade
{
	public LightSensor getLightSensor();
	public UltrasonicSensor getUltraSonicSensor();
	public IRSeekerV2 getInfraRedSensor();
	public TouchSensor getTouchSensor();
}
