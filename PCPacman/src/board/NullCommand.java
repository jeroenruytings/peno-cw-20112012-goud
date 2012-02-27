package board;

import board.protocol.Command;

public class NullCommand implements Command {

	@Override
	public void execute(Simulator simulator) {

	}

	@Override
	public String getNameFrom() {
		return "";
	}

}
