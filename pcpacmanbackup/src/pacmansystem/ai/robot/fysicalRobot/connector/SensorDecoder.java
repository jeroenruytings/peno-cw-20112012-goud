package pacmansystem.ai.robot.fysicalRobot.connector;

public class SensorDecoder extends MessageDecoder
{
	private final byte sensorkey = 0;
	private int value;
	private sensor_type type;

	public enum sensor_type
	{
		LIGHTSENSOR(0), ULTRASONIC(1), PUSH(2), DIRECTIONIRSENSOR(3), VALUEIRSENSOR(
				4), BUTTON(5), TACHOCOUNT(6), HEADTACHO(7);
		public final int mask;

		private sensor_type(int mask)
		{
			this.mask = mask;
		}
	}

	@Override
	public void decode(byte[] message)
	{
		this.reset();
		byte one = message[0];
		for (sensor_type type : sensor_type.values())
			if ((one & _message_mask) == type.mask)
				this.type = type;
		if (this.type == null)
			throw new SensorDecodeException("unknown sensor message code");
		this.value = message[1];
	}

	private void reset()
	{
		this.type = null;
		this.value = 0;
	}

	public Integer value()
	{
		return 0x000000ff & value;
	}

	@Override
	public boolean accepts(byte[] message)
	{
		return (message[0] & _identify_mask) == sensorkey;
	}

	public sensor_type getSensorType()
	{
		return this.type;
	}

}
