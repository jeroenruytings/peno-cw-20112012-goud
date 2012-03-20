package communicator.parser;

import data.world.World;

public interface Command
{

	public String getNameFrom();

	void execute(World simulator);

	public MessageType getMessageType();
	
	public boolean equals(Command cmd);

}
