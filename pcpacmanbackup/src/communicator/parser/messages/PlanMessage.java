package communicator.parser.messages;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import communicator.parser.MessageType;

import data.world.World;

public class PlanMessage extends Message
{

	private String _name;
	private Point[] _path;

	public PlanMessage(String name, Point... path)
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
	void execute(World simulator)
	{
		ArrayList<Point> plan = new ArrayList<Point>();
		for (Point point : _path) {
			plan.add(point);
		}
		simulator.getRobot(_name).addPlan(plan);
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.PLAN;
	}

	@Override
	public boolean equals(Message planMessage) {
		if (planMessage instanceof PlanMessage){
			PlanMessage cmdPlan = (PlanMessage) planMessage;
			if ((cmdPlan.getNameFrom() == this.getNameFrom())
					&& (Arrays.equals(cmdPlan.getPath(), this.getPath())))
				return true;
		}
		return false;
	}

	@Override
	public boolean correctMessage() {
		// TODO Auto-generated method stub
		return false;
	}
}
