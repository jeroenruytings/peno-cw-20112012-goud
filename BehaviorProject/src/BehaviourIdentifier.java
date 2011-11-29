package robot;

public enum BehaviourIdentifier implements Identifier {
	SonarBehaviour(0), LightSensorBehaviour(1), TouchSensorBehaviour(2), GoStraigth(
			3);
	private byte mask;

	private BehaviourIdentifier(int mask) {
		this.setMask((byte) mask);

	}

	@Override
	public byte getMask() {
		return mask;
	}

	@Override
	public boolean validMonitor(Monitor m) {
		return m == Monitor.BehaviorMonitor;
	}

	private void setMask(byte mask) {
		this.mask = mask;
	}

}
