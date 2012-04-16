package communicator.parser.messages;


import communicator.parser.MessageType;

import data.world.World;

public class NullMessage extends Message
{

	@Override
	void execute(World world)
	{
		// DO NOTHING!
	}

	@Override
	public String getNameFrom()
	{
		return "";
	}
	@Override
	public MessageType getMessageType() {
		return MessageType.UNKNOWN;
	}
	
	@Override
	public boolean equals(Message nullMessage) {
		if (nullMessage instanceof NullMessage){
				return true;
		}
		return false;
	}

	@Override
	public boolean correctMessage() {
		return true;
	}

}
