
public enum SensorIdentifier implements Identifier {
	LightSensor(0),UltrasonicSensor(1),BarcodeSensor(2),BROWNMESSAGE(3),BLACKMESSAGE(4),WHITEMESSAGE(5);
	//XXX Maybe add pressing sensors
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
