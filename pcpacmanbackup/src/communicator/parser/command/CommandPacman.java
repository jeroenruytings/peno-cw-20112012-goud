package communicator.parser.command;

import java.awt.Point;

import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

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
