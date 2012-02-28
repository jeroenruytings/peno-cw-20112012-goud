package pacmansystem.parser.command;

import pacmansystem.parser.Command;
import pacmansystem.world.World;

public class NullCommand implements Command {

	@Override
	public void execute(World simulator) {

	}

	@Override
	public String getNameFrom() {
		return "";
	}

}
