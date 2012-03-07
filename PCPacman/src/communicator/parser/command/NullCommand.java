package communicator.parser.command;

import communicator.parser.Command;

import util.world.World;

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
