package communicator.parser.command;

import util.world.World;

import communicator.parser.Command;

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
