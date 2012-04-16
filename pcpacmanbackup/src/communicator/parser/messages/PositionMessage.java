package communicator.parser.messages;

import java.awt.Point;

import communicator.parser.MessageType;

import data.world.World;

public class PositionMessage extends Message
{

	private Point _position;
	private String _name;

	public PositionMessage(String name, Point position)
	{
		this._position = position;
		this._name = name;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	public Point getPosition()
	{
		return _position;
	}

	@Override
	void execute(World world)
	{
		world.getRobot(_name).setPosition(_position);
		synchronized (world.getRobot(_name)) {
			world.getRobot(_name).notify();
		}
		
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.POSITION;
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

}
