package communicator.parser.messages;

import java.awt.Point;

import data.world.World;

public class PositionMessage extends Message
{

	private Point _position;

	public PositionMessage(String nameFrom, Point position)
	{
		super(nameFrom);
		this._position = position;
	}

	public Point getPosition()
	{
		return _position;
	}

	@Override
	void execute(World world)
	{
		world.getRobot(getNameFrom()).setPosition(_position);
		synchronized (world.getRobot(getNameFrom())) {
			world.getRobot(getNameFrom()).notify();
		}
		
	}

	@Override
	public String getKeyword() {
		return "POSITION";
	}
	
	@Override
	public boolean equals(Message positionMessage) {
		if (positionMessage instanceof PositionMessage){
			PositionMessage cmdBar = (PositionMessage) positionMessage;
			if ((cmdBar.getNameFrom() == this.getNameFrom())
					&& (cmdBar.getPosition().equals(this.getPosition())))
				return true;
		}
		return false;
	}

	@Override
	public boolean correctMessage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getParameterString() {
		// TODO Auto-generated method stub
		return null;
	}

}
