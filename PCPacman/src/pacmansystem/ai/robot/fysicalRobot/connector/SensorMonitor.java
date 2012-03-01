package pacmansystem.ai.robot.fysicalRobot.connector;

public class SensorMonitor extends LeoMonitor
{
	private SensorDecoder decoder;
	private MoverLayer virtu;

	public SensorMonitor(LeoMonitor next, MoverLayer virtu)
	{
		super(next);
		this.decoder = new SensorDecoder();
		this.virtu = virtu;
	}

	/**
	 * Accepts the bytes that represent the message send to the gui. This byte
	 * is decoded here and the values of the byte will be
	 * 
	 * @param message
	 */
	@Override
	public void accept(byte[] message)
	{
		if (!this.decoder.accepts(message)) {
			this.next().accept(message);
			return;
		}
		this.decoder.decode(message);
		switch (decoder.getSensorType())
		{
		case PUSH:
			// gevolg invullen
//			System.out.println("druksensor: " + decoder.value());
			break;
		case DIRECTIONIRSENSOR:
			// gevolg invullen
//			System.out.println("direction irsensor: " + decoder.value());
		case VALUEIRSENSOR:
			// gevolg invullen
//			System.out.println("value irsensor: " + decoder.value());
		case LIGHTSENSOR:
			// gevolg invullen
//			System.out.println("lichtsensor: " + decoder.value());
			virtu.setLightSensor(decoder.value());
			break;
		case ULTRASONIC:
			// gevolg invullen
//			System.out.println("afstandssensor: " + decoder.value());
			break;
		case BUTTON:
			virtu.pushButton();
//			System.out.println(virtu.buttonIsPushed());
			break;
		case TACHOCOUNT:
//			System.out.println("tachocount: " + decoder.value());
			virtu.setTachoCount(decoder.value());
			
		}

	}

}
