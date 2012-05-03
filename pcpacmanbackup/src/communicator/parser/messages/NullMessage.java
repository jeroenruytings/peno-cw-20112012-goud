package communicator.parser.messages;

import data.world.World;

public class NullMessage extends Message
{
	public NullMessage(){
		super("");
	}
	
	@Override
	public boolean canExecute(World world){
		return true;
	}
	
	@Override
	void execute(World world)
	{
		if (!canExecute(world))
			throw new MessageExecuteException();
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
	protected boolean equalParameters(Message cmd) {
		if (cmd instanceof NullMessage)
			return true;
		return false;
	}

}
