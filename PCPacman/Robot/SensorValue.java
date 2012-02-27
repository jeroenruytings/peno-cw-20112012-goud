
public class SensorValue implements Value {
	/**
	 * We can only sent bytes
	 * only raw values are sent
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
