package robot;

public enum ActionLogIdentifier implements Identifier {
	Forward(0),TurnLeft(1),TurnRight(2),Barcode(3);
	private byte mask;
	private ActionLogIdentifier(int b)
	{
		this.mask=(byte)b;
	}
	@Override
	public byte getMask() {
		return mask;
	}

	@Override
	public boolean validMonitor(Monitor m) {
		return false;
	}

}
