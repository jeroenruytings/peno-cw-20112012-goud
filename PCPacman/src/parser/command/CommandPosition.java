package parser.command;

import java.awt.Point;

import parser.Command;

import board.Simulator;

public class CommandPosition implements Command {

	private Point _position; 
	private String _name;
	
	public CommandPosition (String name, Point position){
		this._position = position;
		this._name = name;
	}
	
	
	@Override
	public String getNameFrom() {
		return _name;
	}
	
	public Point getPosition(){
		return _position;
	}

	@Override
	public void execute(Simulator simulator) {
		// TODO Auto-generated method stub

	}

}
