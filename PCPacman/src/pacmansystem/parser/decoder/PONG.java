package pacmansystem.parser.decoder;

import java.text.ParseException;

import pacmansystem.parser.Command;
import pacmansystem.parser.Decoder;
import pacmansystem.parser.command.CommandPong;

public class PONG extends Decoder
{

	protected PONG(Decoder next)
	{
		super(next, "PONG");
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
		return new CommandPong(mes[0],mes[2],mes[3]);
	}

}
