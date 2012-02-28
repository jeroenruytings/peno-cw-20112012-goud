package pacmansystem.parser.decoder;

import pacmansystem.parser.Command;
import pacmansystem.parser.Decoder;
import pacmansystem.parser.command.CommandJoin;

public class JOIN extends Decoder
{

	public JOIN(Decoder next)
	{
		super(next, "JOIN");
	}

	public JOIN()
	{
		super("JOIN");
	}

	@Override
	public boolean canDecode(String message)
	{
		return message.equals("JOIN");
	}

	@Override
	public Command parse(String message)
	{
		return new CommandJoin();
	}

}
