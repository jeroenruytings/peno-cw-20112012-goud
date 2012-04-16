package communicator.parser.messages;

import data.world.World;

public abstract class Message {
	
	public Message(String nameFrom){
		_nameFrom = nameFrom;
	}
	
	public String getNameFrom(){
		return _nameFrom;
	}

	private String _nameFrom;

	public abstract String getKeyword();
	
	
	protected abstract String getParameterString();
	
	
	public String getSentString(){
		return getNameFrom() + getKeyword() + getParameterString();
	}

	public abstract boolean correctMessage();
	
	abstract void execute(World world);

	public abstract boolean equals(Message cmd);
	
	
}
