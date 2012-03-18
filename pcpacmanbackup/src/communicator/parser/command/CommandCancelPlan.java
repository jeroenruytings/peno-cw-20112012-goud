package communicator.parser.command;

import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

public class CommandCancelPlan implements Command
{

	private String _name;

	public CommandCancelPlan(String _name)
	{
		this._name = _name;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	@Override
	public void execute(World simulator)
	{
		simulator.getRobot(_name).clearPlan();
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.CANCELPLAN;
	}
	
	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandCancelPlan){
			CommandCancelPlan cmdCnlPl = (CommandCancelPlan) cmd;
			if ((cmdCnlPl.getNameFrom() == this.getNameFrom()))
				return true;
		}
		return false;
	}
	
	

}
