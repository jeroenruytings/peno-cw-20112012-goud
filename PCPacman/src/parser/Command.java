package parser;

import world.World;
import board.Simulator;

public interface Command {
	
	public String getNameFrom();
	
	void execute(World simulator);

}
