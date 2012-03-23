package communicator.parser.command;

import java.awt.Point;


import communicator.parser.Command;
import communicator.parser.MessageType;
import data.board.Board;
import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Orientation;
import data.world.World;

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
		result.setBorder(Orientation.NORTH, WallState.values()[_north]);
		result.setBorder(Orientation.EAST, WallState.values()[_east]);
		result.setBorder(Orientation.SOUTH, WallState.values()[_south]);
		result.setBorder(Orientation.WEST, WallState.values()[_west]);
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
			else if(!p.equals(b.getPanelAt(_coordinate))){
				Panel e = b.getPanelAt(_coordinate);
				for(Orientation d : Orientation.values()){
					if(e.getWallState(d) == WallState.UNKNOWN)
					e.setBorder(d, p.getWallState(d));
				}
				b.add(e, _coordinate);
			}
			
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
