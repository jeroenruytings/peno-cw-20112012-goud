package communicator.parser.messages;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import data.world.World;

public class PlanMessage extends Message
{

	private Point[] _path;

	public PlanMessage(String nameFrom, Point... path)
	{
		super(nameFrom);
		for (Point point : path)
			setParameter(point);
		_path = path;
	}

	public Point[] getPath()
	{
		return _path;

	}

	@Override
	void execute(World world)
	{
		if (!canExecute(world))
			throw new MessageExecuteException();
		ArrayList<Point> plan = new ArrayList<Point>();
		for (Point point : _path) {
			plan.add(point);
		}
		world.getRobot(getNameFrom()).addPlan(plan);
		synchronized (world.getRobot(getNameFrom())) {
			world.getRobot(getNameFrom()).notify();
		}
	}

	@Override
	public String getKeyword() {
		return "PLAN";
	}

	@Override
	protected boolean equalParameters(Message planMessage) {
		if (planMessage instanceof PlanMessage){
			PlanMessage cmdPlan = (PlanMessage) planMessage;
			if (Arrays.equals(cmdPlan.getPath(), this.getPath()))
				return true;
		}
		return false;
	}
}
