package pacmansystem.parser.command;

import pacmansystem.parser.Command;
import pacmansystem.world.World;

public class CommandPong implements Command {

	private String _name;
	private String _bestemmeling;
	private String _string;
	
	
	public CommandPong(String name, String bestemmeling, String string) {
		this._name = name;
		this._bestemmeling = bestemmeling;
		this._string = string;
	}

	@Override
	public String getNameFrom() {
		return _name;
	}
	
	/**
	 * @return The name of the recipient.
	 */
	public String getBestemmeling(){
		return _bestemmeling;
	}
	
	/**
	 * @return The string passed along to the pong commando.
	 */
	public String getString(){
		return _string;
	}

	@Override
	public void execute(World simulator) {
		// TODO Auto-generated method stub

	}

}
