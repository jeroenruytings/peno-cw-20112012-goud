package robot;

public enum Monitor {
	SensorMonitor(0),BehaviorMonitor(64),ActionLogMonitor(128);
	private byte mask;
	private Monitor(int mask){
		this.setMask((byte) mask);
	}
	private void setMask(byte mask) {
		this.mask = mask;
	}
	public byte getMask() {
		return mask;
	}
}
