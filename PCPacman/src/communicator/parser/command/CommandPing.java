package communicator.parser.command;

import util.world.World;

import communicator.parser.Command;

public class CommandPing implements Command {

	private String _name;
	private String _bestemmeling;
	private String _string;
	
	public CommandPing(String name, String bestemmeling, String string){
		this._name = name;
		this._bestemmeling = bestemmeling;
		this._string = string;
	}
	
	@Override
	public String getNameFrom() {
		return _name;
	}
	
	public String getBestemmeling(){
		return _bestemmeling;
	}
	
	public String getString(){
		return _string;
	}

	@Override
	public void execute(World simulator) {
		// TODO Auto-generated method stub

	}

}
