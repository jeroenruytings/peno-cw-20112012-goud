package communicator.parser.command;

import util.world.World;

import communicator.parser.Command;

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

}
