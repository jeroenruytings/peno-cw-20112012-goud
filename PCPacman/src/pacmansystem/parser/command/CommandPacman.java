package pacmansystem.parser.command;

import java.awt.Point;

import pacmansystem.parser.Command;
import pacmansystem.world.World;

public class CommandPacman implements Command
{

	private String _name;
	private Point _coordinate;

	public CommandPacman(String name, Point coordinate)
	{
		this._name = name;
		this._coordinate = coordinate;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	public Point getPosition()
	{
		return _coordinate;
	}

	@Override
	public void execute(World simulator)
	{
		simulator.getRobot(_name).setPacman(_coordinate);
	}

}
