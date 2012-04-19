package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.PingMessage;


public class PingMessageDecoder extends MessageDecoder<PingMessage>
{

	protected PingMessageDecoder(MessageDecoder<? extends Message> next)
	{
		super(next, "PING");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public PingMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new PingMessage(mes[0], mes[2], mes[3]);
	}

}
