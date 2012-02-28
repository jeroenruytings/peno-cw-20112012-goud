package pacmansystem.parser;

import pacmansystem.world.World;

public interface Command
{

	public String getNameFrom();

	void execute(World simulator);

}
