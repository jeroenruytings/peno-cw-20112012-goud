package board;

import board.protocol.Command;

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
	public void execute(Simulator simulator) {
		// TODO Auto-generated method stub

	}

}
