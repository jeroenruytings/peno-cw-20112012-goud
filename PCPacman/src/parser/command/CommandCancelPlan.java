package parser.command;

import parser.Command;
import world.World;

public class CommandCancelPlan implements Command {

	private String _name;

	public CommandCancelPlan(String _name){
		this._name = _name;
	}
	
	@Override
	public String getNameFrom(){
		return _name;
	}

	@Override
	public void execute(World simulator) {
		simulator.getRobot(_name).clearPlan();
	}

}