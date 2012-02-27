package board;

import board.protocol.Command;

public class CommandName implements Command {
	private String _name;
	private int _version;

	public CommandName(String name,int version)
	{
		_name=name;
		_version=version;
	}
	@Override
	public String getNameFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Simulator simulator) {
		// TODO Auto-generated method stub

	}

}
