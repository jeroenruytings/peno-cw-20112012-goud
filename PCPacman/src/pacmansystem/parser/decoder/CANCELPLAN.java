package pacmansystem.parser.decoder;

import java.text.ParseException;

import pacmansystem.parser.Command;
import pacmansystem.parser.Decoder;
import pacmansystem.parser.command.CommandCancelPlan;

public class CANCELPLAN extends Decoder
{

	public CANCELPLAN(Decoder next)
	{
		super(next, "CANCELPLAN");
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
		String[] mes = message.split(" ");
		return new CommandCancelPlan(mes[0]);
	}

}
