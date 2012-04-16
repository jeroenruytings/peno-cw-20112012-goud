package communicator.parser.messages;

import communicator.parser.MessageType;

import data.world.World;

public abstract class Message {
	
	
	abstract void execute(World world);
	
	public abstract MessageType getMessageType();
	
	public abstract boolean correctMessage();
	
	public abstract String getNameFrom();
	
	public abstract boolean equals(Message cmd);
}
