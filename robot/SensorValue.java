package robot;

public class SensorValue implements Value {
	/**
	 * We can only sent bytes, Distances are sent in cm(whole values)!
	 * NOT MM  !!!!!
	 * DO NOT SENT DISTANCES IN MM FFS
	 * @param value
	 */
	public SensorValue(byte value)
	{
		this.mask=value;
	}
	private byte mask;
	@Override
	public byte getMask() {
		// TODO Auto-generated method stub
		return mask;
	}

}
