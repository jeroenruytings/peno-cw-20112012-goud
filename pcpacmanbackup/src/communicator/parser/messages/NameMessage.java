package communicator.parser.messages;


import communicator.parser.MessageType;

import data.world.InsufficientJoinsException;
import data.world.World;

public class NameMessage extends Message
{
	private String _name;
	private String _version;

	public NameMessage(String name, String version)
	{
		_name = name;
		_version = version;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	public String getVersion()
	{
		return _version;
	}

	@Override
	public void execute(World world)
	{
		try {
			synchronized (world) {
				world.notify();
			}
			world.addRobot(getNameFrom());
		} catch (InsufficientJoinsException e) {
			System.err.print("Zoek de schuldige, er zijn niet genoeg robots.");
		}
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.NAME;
	}
	
	@Override
	public boolean equals(Message nameMessage) {
		if (nameMessage instanceof NameMessage){
			NameMessage cmdName = (NameMessage) nameMessage;
			if ((cmdName.getNameFrom() == this.getNameFrom())
					&& (cmdName.getVersion().equals(this.getVersion())))
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
