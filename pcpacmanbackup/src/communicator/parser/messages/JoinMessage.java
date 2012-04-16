package communicator.parser.messages;


import communicator.parser.MessageType;

import data.world.World;

public class JoinMessage extends Message
{

	public JoinMessage()
	{
		
	}

	@Override
	void execute(World simulator)
	{
		simulator.register();
	}

	@Override
	public String getNameFrom()
	{
		return "";
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.JOIN;
	}
	
	@Override
	public boolean equals(Message joinMessage) {
		if (joinMessage instanceof JoinMessage){
				return true;
		}
		return false;
	}

	@Override
	public boolean correctMessage() {
		// TODO Auto-generated method stub
		return false;
	}

}
