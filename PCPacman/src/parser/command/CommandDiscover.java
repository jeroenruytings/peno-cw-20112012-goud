package parser.command;

import java.awt.Point;

import parser.Command;
import world.World;


import board.Board;
import board.Orientation;
import board.Panel;

public class CommandDiscover implements Command {

	private String _name;
	private Point _coordinate;
	private byte _north;
	private byte _east;
	private byte _south;
	private byte _west;
	
	
	public CommandDiscover(String name, Point coordinate, byte north, byte east, byte south, byte west){
		this._name = name;
		this._coordinate = coordinate;
		this._north = north;
		this._east = east;
		this._south = south;
		this._west = west;
	}
	
	@Override
	public String getNameFrom() {
		return _name;
	}
	
	public Point getCoordinate(){
		return _coordinate;
	}
	
	public Panel getPanel(){
		Panel result = new Panel();
		// TODO: Ternairy values of discover are NOT implemented. 
		result.setBorder(Orientation.NORTH, _north > 0);
		result.setBorder(Orientation.EAST, _east > 0);
		result.setBorder(Orientation.SOUTH, _south > 0);
		result.setBorder(Orientation.WEST, _west > 0);
		return result;
	}

	@Override
	public void execute(World simulator) {
		Panel p = getPanel();
		Board b = simulator.getRobot(_name).getBoard();
		b.add(p, simulator.getRobot(_name).getPosition());
	}

}
