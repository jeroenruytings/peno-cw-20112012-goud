package communicator.parser;

import communicator.parser.command.NullCommand;

public class NullDecoder extends Decoder
{

	
	public NullDecoder()
	{
		super(null,"");
	}

	@Override
	public boolean canDecode(String message)
	{
		return true;
	}

	@Override
	public Command parse(String message)
	{
		System.err.println("Het bericht: \"" + message + "\"" + " is weggesmeten");
		return new NullCommand();
	}

}
