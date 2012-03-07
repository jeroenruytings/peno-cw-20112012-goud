package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.Command;
import communicator.parser.Decoder;
import communicator.parser.command.CommandPing;


public class PING extends Decoder
{

	protected PING(Decoder next)
	{
		super(next, "PING");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public Command parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new CommandPing(mes[0], mes[2], mes[3]);
	}

}
