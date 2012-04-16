package communicator.parser.messages;

import data.world.World;

public class JoinMessage extends Message
{

	public JoinMessage()
	{
		super("");
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
	public String getKeyword() {
		return "JOIN";
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

	@Override
	protected String getParameterString() {
		// TODO Auto-generated method stub
		return null;
	}

}
