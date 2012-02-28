package parser.command;

import java.awt.Point;

import parser.Command;

import board.Simulator;

public class CommandPacman implements Command {

	private String _name;
	private Point _coordinate;
	
	
	public CommandPacman(String name, Point coordinate){
		this._name = name;
		this._coordinate = coordinate;
	}
	
	@Override
	public String getNameFrom() {
		return _name;
	}
	
	public Point getPosition(){
		return _coordinate;
	}

	@Override
	public void execute(Simulator simulator) {
		// TODO Auto-generated method stub

	}

}
