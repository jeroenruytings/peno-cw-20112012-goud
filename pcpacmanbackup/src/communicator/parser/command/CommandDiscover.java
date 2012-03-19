package communicator.parser.command;

import java.awt.Point;

import util.board.Board;
import util.board.Panel;
import util.enums.Orientation;
import util.world.World;

import communicator.parser.Command;
import communicator.parser.MessageType;

public class CommandDiscover implements Command
{

	private String _name;
	private Point _coordinate;
	private byte _north;
	private byte _east;
	private byte _south;
	private byte _west;

	public CommandDiscover(String name, Point coordinate, int i,
			int j, int k, int l)
	{
		this._name = name;
		this._coordinate = coordinate;
		this._north = (byte) i;
		this._east = (byte) j;
		this._south =(byte) k;
		this._west = (byte) l;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	public Point getCoordinate()
	{
		return _coordinate;
	}

	public Panel getPanel()
	{
		Panel result = new Panel();
		// TODO: Ternairy values of discover are NOT implemented.
		result.setBorder(Orientation.NORTH, _north > 0);
		result.setBorder(Orientation.EAST, _east > 0);
		result.setBorder(Orientation.SOUTH, _south > 0);
		result.setBorder(Orientation.WEST, _west > 0);
		return result;
	}

	@Override
	public void execute(World world)
	{
		Panel p = getPanel();
		if (world.getRobot(_name) != null){
			Board b = world.getRobot(_name).getBoard();
			if(!b.hasPanelAt(_coordinate))
				b.add(p, _coordinate);
			synchronized (world.getRobot(_name)) {
				world.getRobot(_name).notify();
			}
		}
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.DISCOVER;
	}
	
	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandDiscover){
			CommandDiscover cmdDis = (CommandDiscover) cmd;
			if ((cmdDis.getNameFrom() == this.getNameFrom())
					&& (cmdDis.getCoordinate().equals(this.getCoordinate()))
					&& (cmdDis.getPanel().equals(this.getPanel())))
				return true;
		}
		return false;
	}

}
