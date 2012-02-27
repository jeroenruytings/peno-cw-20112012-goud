package board.protocol;

import board.Simulator;

public interface Command {
	public String getNameFrom();
	public void execute(Simulator simulator);

}
