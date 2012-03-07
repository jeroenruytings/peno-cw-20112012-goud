package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.Command;
import communicator.parser.Decoder;
import communicator.parser.command.CommandCaptured;


public class CAPTURED extends Decoder
{

	public CAPTURED(Decoder next)
	{
		super(next, "CAPTURED");
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
		return new CommandCaptured(mes[0]);
	}

}
