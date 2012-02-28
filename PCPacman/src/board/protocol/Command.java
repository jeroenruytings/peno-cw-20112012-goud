package board.protocol;

import board.Simulator;
import board.World;

public interface Command {
	public String getNameFrom();
	void execute(World simulator);

}
