package communicator.parser.command;

import util.world.InsufficientJoinsException;
import util.world.World;

import communicator.parser.Command;

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
			simulator.addRobot(getNameFrom());
		} catch (InsufficientJoinsException e) {
			System.err.print("Zoek de schuldige, er zijn niet genoeg robots.");
		}
	}

}
