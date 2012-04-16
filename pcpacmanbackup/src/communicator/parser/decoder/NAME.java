package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.Decoder;
import communicator.parser.messages.NameMessage;


public class NAME extends Decoder
{

	protected NAME(Decoder next)
	{
		super(next, "NAME");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public NameMessage parse(String message) throws ParseException
	{
		if (!canDecode(message))
			throw new ParseException("exc", 0);
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new NameMessage(mes[0], mes[2]);
	}

}
