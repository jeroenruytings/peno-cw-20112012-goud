package communicator.parser;

import util.world.World;

public interface Command
{

	public String getNameFrom();

	void execute(World simulator);

}
