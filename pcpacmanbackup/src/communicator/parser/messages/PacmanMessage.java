package communicator.parser.messages;

import java.awt.Point;

import data.world.World;

public class PacmanMessage extends Message
{

	private Point _coordinate;

	public PacmanMessage(String nameFrom, Point coordinate)
	{
		super(nameFrom);
		setParameter(coordinate);
		this._coordinate = coordinate;
	}

	public Point getPosition()
	{
		return _coordinate;
	}

	@Override
	public void execute(World world)
	{
		if (!canExecute(world))
			throw new MessageExecuteException();
		world.getRobot(getNameFrom()).setPacman(_coordinate);
		synchronized (world.getRobot(getNameFrom())) {
			world.getRobot(getNameFrom()).notify();
		}
		
	}

	@Override
	public String getKeyword() {
		return "PACMAN";
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

}
