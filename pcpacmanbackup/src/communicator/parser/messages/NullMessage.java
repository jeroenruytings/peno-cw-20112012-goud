package communicator.parser.messages;

import data.world.World;

public class NullMessage extends Message
{
	public NullMessage(){
		super("");
	}
	
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
	public String getKeyword() {
		return "UNKNOWN";
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

	@Override
	protected String getParameterString() {
		return "";
	}

}
