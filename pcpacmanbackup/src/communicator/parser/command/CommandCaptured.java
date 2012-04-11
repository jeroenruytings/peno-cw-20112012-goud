package communicator.parser.command;


import communicator.parser.Command;
import communicator.parser.MessageType;

import data.world.World;

public class CommandCaptured implements Command
{

	private String _name;

	public CommandCaptured(String name)
	{
		this._name = name;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	@Override
	public void execute(World simulator)
	{
		simulator.getRobot(_name).setCapturedPacman(true);
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.CAPTURED;
	}
	
	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandCaptured){
			CommandCaptured cmdBar = (CommandCaptured) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom()))
				return true;
		}
		return false;
	}

}
