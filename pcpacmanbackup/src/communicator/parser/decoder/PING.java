package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.Decoder;
import communicator.parser.messages.PingMessage;


public class PING extends Decoder
{

	protected PING(Decoder next)
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
