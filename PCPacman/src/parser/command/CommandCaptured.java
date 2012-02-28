package parser.command;

import parser.Command;
import world.World;

public class CommandCaptured implements Command {

	private String _name;
	
	
	public CommandCaptured(String name){
		this._name = name;
	}
	
	
	@Override
	public String getNameFrom() {
		return _name;
	}

	@Override
	public void execute(World simulator) {
		simulator.getRobot(_name).setCapturedPacman(true);
	}

}
