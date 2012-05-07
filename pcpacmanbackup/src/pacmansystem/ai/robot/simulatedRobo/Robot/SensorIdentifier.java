package pacmansystem.ai.robot.simulatedRobo.Robot;

public enum SensorIdentifier implements Identifier {
	LightSensor(0),UltrasonicSensor(1),PushSensor(2),DirectionIrSensor(3), ValueIrSensor(4), ButtonPressed(5), TachoCount(6), HeadTacho(7);
//	HeadTacho(7);

	private byte mask;
	private SensorIdentifier(int mask){
		this.mask=(byte) mask;
	}
	@Override
	public byte getMask() {
		// TODO Auto-generated method stub
		return mask;
	}

	@Override
	public boolean validMonitor(Monitor m) {
		// TODO Auto-generated method stub
		return m==Monitor.SensorMonitor;
	}
}
