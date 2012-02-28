package pacmansystem.parser.command;

import java.awt.Point;
import java.util.ArrayList;

import pacmansystem.parser.Command;
import pacmansystem.world.World;

public class CommandPlan implements Command
{

	private String _name;
	private Point[] _path;

	public CommandPlan(String name, Point... path)
	{

	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	public Point[] getPath()
	{
		return _path;

	}

	@Override
	public void execute(World simulator)
	{
		ArrayList<Point> plan = new ArrayList<Point>();
		for (Point point : _path) {
			plan.add(point);
		}
		simulator.getRobot(_name).addPlan(plan);
	}

}
