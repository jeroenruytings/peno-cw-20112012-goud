package pacmansystem.parser.decoder;

import java.text.ParseException;

import pacmansystem.parser.Command;
import pacmansystem.parser.Decoder;
import pacmansystem.parser.command.CommandName;

public class NAME extends Decoder
{

	protected NAME(Decoder next)
	{
		super(next, "NAME");
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
		if (!canDecode(message))
			throw new ParseException("exc", 0);
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new CommandName(mes[0], new Integer(mes[2]));
	}

}
