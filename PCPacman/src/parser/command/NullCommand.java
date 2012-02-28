package parser.command;

import parser.Command;
import world.World;

public class NullCommand implements Command {

	@Override
	public void execute(World simulator) {

	}

	@Override
	public String getNameFrom() {
		return "";
	}

}
