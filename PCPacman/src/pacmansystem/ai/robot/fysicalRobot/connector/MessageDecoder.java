package pacmansystem.ai.robot.fysicalRobot.connector;


public abstract class MessageDecoder {
	protected static byte _identify_mask = (byte) 192;
	protected static byte _message_mask = 63;
	public abstract void decode(byte[] message);
	public abstract boolean accepts(byte[] message);
}
