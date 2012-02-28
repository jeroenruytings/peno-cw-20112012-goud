package Robot;

public enum SensorIdentifier implements Identifier {
	LightSensor(0),UltrasonicSensor(1),PushSensor(2),DirectionIrSensor(3), ValueIrSensor(4);

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
