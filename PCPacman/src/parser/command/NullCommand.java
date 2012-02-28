package parser.command;

import parser.Command;
import board.Simulator;

public class NullCommand implements Command {

	@Override
	public void execute(Simulator simulator) {

	}

	@Override
	public String getNameFrom() {
		return "";
	}

}
