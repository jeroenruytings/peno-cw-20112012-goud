package parser;

import world.World;

public interface Command {
	
	public String getNameFrom();
	
	void execute(World simulator);

}
