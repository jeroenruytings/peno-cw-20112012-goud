
public enum ActionLogIdentifier implements Identifier {
	Forward(0),TurnLeft(1),TurnRight(2), UNSPECIFIEDFORWARD(3),Barcode(4), FORWARDMILIMETERS(5);
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
