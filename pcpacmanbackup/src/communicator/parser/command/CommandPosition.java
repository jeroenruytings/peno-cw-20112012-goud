package communicator.parser.command;

import java.awt.Point;

import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

public class CommandPosition implements Command
{

	private Point _position;
	private String _name;

	public CommandPosition(String name, Point position)
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
	public void execute(World world)
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
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandPosition){
			CommandPosition cmdBar = (CommandPosition) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom())
					&& (cmdBar.getPosition().equals(this.getPosition())))
				return true;
		}
		return false;
	}

}
