package parser.command;

import java.awt.Point;

import parser.Command;
import world.World;

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
	public void execute(World simulator) {
		simulator.getRobot(_name).setPosition(_position);
	}

}
