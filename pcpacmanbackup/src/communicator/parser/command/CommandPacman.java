package communicator.parser.command;

import java.awt.Point;


import communicator.parser.Command;
import communicator.parser.MessageType;
import data.world.World;

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
	public void execute(World world)
	{
		world.getRobot(_name).setPacman(_coordinate);

		world.getRobot(_name).notify();
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.PACMAN;
	}

	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandPacman){
			CommandPacman cmdBar = (CommandPacman) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom())
					&& (cmdBar.getPosition().equals(this.getPosition())))
				return true;
		}
		return false;
	}

}
