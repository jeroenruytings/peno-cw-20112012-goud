package parser.command;

import java.awt.Point;

import parser.Command;

import board.Simulator;

public class CommandPlan implements Command {

	private String _name;
	private Point[] _path;
	
	public CommandPlan(String name, Point ... path){
		
	}
	
	
	@Override
	public String getNameFrom() {
		return _name;
	}
	
	public Point[] getPath(){
		return _path;
		
	}

	@Override
	public void execute(Simulator simulator) {
		// TODO Auto-generated method stub

	}

}
