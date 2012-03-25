package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.Command;
import communicator.parser.Decoder;
import communicator.parser.command.CommandCancelPlan;


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
		String msg = stripMessage(message);//TODO: we kregen een error hierdoor
		String[] mes = msg.split(" ");
		return new CommandCancelPlan(mes[0]);
	}

}
