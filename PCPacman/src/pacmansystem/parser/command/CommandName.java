package pacmansystem.parser.command;

import pacmansystem.parser.Command;
import pacmansystem.world.RobotData;
import pacmansystem.world.World;

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
		simulator.get_robots().put(getNameFrom(), new RobotData());
	}

}
