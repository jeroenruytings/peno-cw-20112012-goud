package communicator.parser.command;

import util.world.World;

import communicator.parser.Command;

public class NullCommand implements Command
{

	@Override
	public void execute(World simulator)
	{

	}

	@Override
	public String getNameFrom()
	{
		return "";
	}

}
