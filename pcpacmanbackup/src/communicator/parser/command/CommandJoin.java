package communicator.parser.command;

import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

public class CommandJoin implements Command
{

	public CommandJoin()
	{
		
	}

	@Override
	public void execute(World simulator)
	{
		simulator.register();
	}

	@Override
	public String getNameFrom()
	{
		return "";
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.JOIN;
	}
	
	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandJoin){
				return true;
		}
		return false;
	}

}
