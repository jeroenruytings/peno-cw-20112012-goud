package communicator.parser.command;

import util.world.InsufficientJoinsException;
import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

public class CommandName implements Command
{
	private String _name;
	private String _version;

	public CommandName(String name, String version)
	{
		_name = name;
		_version = version;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	public String getVersion()
	{
		return _version;
	}

	@Override
	public void execute(World simulator)
	{
		try {
			synchronized (simulator) {
				simulator.notify();
			}
			simulator.addRobot(getNameFrom());
		} catch (InsufficientJoinsException e) {
			System.err.print("Zoek de schuldige, er zijn niet genoeg robots.");
		}
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.NAME;
	}
	
	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandName){
			CommandName cmdName = (CommandName) cmd;
			if ((cmdName.getNameFrom() == this.getNameFrom())
					&& (cmdName.getVersion().equals(this.getVersion())))
				return true;
		}
		return false;
	}

}
