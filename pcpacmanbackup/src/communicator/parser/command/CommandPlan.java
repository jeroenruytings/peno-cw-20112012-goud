package communicator.parser.command;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;


import communicator.parser.Command;
import communicator.parser.MessageType;
import data.world.World;

public class CommandPlan implements Command
{

	private String _name;
	private Point[] _path;

	public CommandPlan(String name, Point... path)
	{
		this._name = name;
		_path = path;
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
	//	ArrayList<Point> plan = new ArrayList<Point>();
		//for (Point point : _path) {
		//	plan.add(point);
		//}
		//simulator.getRobot(_name).addPlan(plan);
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.PLAN;
	}

	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandPlan){
			CommandPlan cmdPlan = (CommandPlan) cmd;
			if ((cmdPlan.getNameFrom() == this.getNameFrom())
					&& (Arrays.equals(cmdPlan.getPath(), this.getPath())))
				return true;
		}
		return false;
	}
}
