package communicator.parser.messages;

import java.awt.Point;

import communicator.parser.MessageType;

import data.world.World;

public class PacmanMessage extends Message
{

	private String _name;
	private Point _coordinate;

	public PacmanMessage(String name, Point coordinate)
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
	public void execute(World world)
	{
		world.getRobot(_name).setPacman(_coordinate);
		synchronized (world.getRobot(_name)) {
			world.getRobot(_name).notify();
		}
		
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.PACMAN;
	}

	@Override
	public boolean equals(Message pacmanMessage) {
		if (pacmanMessage instanceof PacmanMessage){
			PacmanMessage cmdBar = (PacmanMessage) pacmanMessage;
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
