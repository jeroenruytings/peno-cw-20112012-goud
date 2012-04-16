package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.Decoder;
import communicator.parser.messages.PongMessage;


public class PONG extends Decoder
{

	protected PONG(Decoder next)
	{
		super(next, "PONG");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public PongMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new PongMessage(mes[0],mes[2],mes[3]);
	}

}
