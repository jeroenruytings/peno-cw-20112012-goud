package communicator.parser.command;


import communicator.parser.Command;
import communicator.parser.MessageType;

import data.world.World;

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
