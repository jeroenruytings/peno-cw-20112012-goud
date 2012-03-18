package communicator.parser.command;

import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

public class NullCommand implements Command
{

	@Override
	public void execute(World simulator)
	{

	}

	@Override
	public String getNameFrom()
	{
		return "";
	}
	@Override
	public MessageType getMessageType() {
		return MessageType.UNKNOWN;
	}
	
	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof NullCommand){
				return true;
		}
		return false;
	}

}
