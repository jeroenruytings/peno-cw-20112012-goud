package communicator.parser.command;

import communicator.parser.Command;

import util.world.World;

public class CommandJoin implements Command
{

	public CommandJoin()
	{
		
	}

	@Override
	public void execute(World simulator)
	{
		simulator.register();
	}

	@Override
	public String getNameFrom()
	{
		return "";
	}

}
