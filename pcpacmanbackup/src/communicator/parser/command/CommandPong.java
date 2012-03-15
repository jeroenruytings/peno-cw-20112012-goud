package communicator.parser.command;

import util.world.World;

import communicator.parser.Command;

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
		// TODO Hebben we zelf een ping uitgestuurt naar deze persoon?

	}

}
