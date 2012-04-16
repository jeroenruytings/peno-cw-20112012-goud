package communicator.parser.messages;


import communicator.parser.MessageType;

import data.world.World;

public class CapturedMessage extends Message
{

	private String _name;

	public CapturedMessage(String name)
	{
		this._name = name;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	@Override
	void execute(World simulator)
	{
		simulator.getRobot(_name).setCapturedPacman(true);
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.CAPTURED;
	}
	
	@Override
	public boolean equals(Message cmd) {
		if (cmd instanceof CapturedMessage){
			CapturedMessage cmdBar = (CapturedMessage) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom()))
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
