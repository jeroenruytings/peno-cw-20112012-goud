package communicator.parser.messages;

import java.awt.Point;

import data.board.Board;
import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Orientation;
import data.world.World;

public class DiscoverMessage extends Message
{

	private Point _coordinate;
	private byte _north;
	private byte _east;
	private byte _south;
	private byte _west;

	public DiscoverMessage(String nameFrom, Point coordinate, int n,
			int e, int s, int w)
	{
		super(nameFrom);
		setParameter(coordinate);
		setParameter(n);
		setParameter(e);
		setParameter(s);
		setParameter(w);
		this._coordinate = coordinate;
		this._north = (byte) n;
		this._east = (byte) e;
		this._south =(byte) s;
		this._west = (byte) w;
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
		if (world.getRobot(getNameFrom()) != null){
			Board b = world.getRobot(getNameFrom()).getBoard();
			if(!b.hasPanelAt(_coordinate))
				b.add(p, _coordinate);
			else if(!p.equals(b.getPanelAt(_coordinate))){
				Panel e = b.getPanelAt(_coordinate);
				for(Orientation d : Orientation.values()){
					if(e.getWallState(d) == WallState.UNKNOWN)
					e.setBorder(d, p.getWallState(d));
					if(e.getWallState(d) != p.getWallState(d)){
						e.setBorder(d, p.getWallState(d));
					}
				}
				try{
					b.add(e, _coordinate);
				}catch (IllegalArgumentException err){
					b.addForced(e,_coordinate);
					System.err.println(_coordinate);
				}
			}
			
			synchronized (world.getRobot(getNameFrom())) {
				world.getRobot(getNameFrom()).notify();
			}
		}
	}

	@Override
	public String getKeyword() {
		return "DISCOVER";
	}
	
	@Override
	public boolean equals(Message cmd) {
		if (cmd instanceof DiscoverMessage){
			DiscoverMessage cmdDis = (DiscoverMessage) cmd;
			if ((cmdDis.getNameFrom() == this.getNameFrom())
					&& (cmdDis.getCoordinate().equals(this.getCoordinate()))
					&& (cmdDis.getPanel().equals(this.getPanel())))
				return true;
		}
		return false;
	}
	
	public int getN(){
		return _north;
	}
	
	public int getE(){
		return _east;
	}
	
	public int getS(){
		return _south;
	}
	
	public int getW(){
		return _west;
	}

	@Override
	public Message getShowMapMessage() {
		return new RediscoverMessage(getNameFrom(), getCoordinate(), getN(), getE(), getS(), getW());
	}
	
	

}
