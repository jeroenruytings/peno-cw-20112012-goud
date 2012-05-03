package communicator.parser.messages;

import data.world.World;

public class JoinMessage extends Message
{

	public JoinMessage()
	{
		super("");
	}

	public boolean canExecute(World world){
		return true;
	}
	
	@Override
	void execute(World world)
	{
		if(!canExecute(world))
			throw new MessageExecuteException();
		world.register();
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
	protected boolean equalParameters(Message joinMessage) {
		if (joinMessage instanceof JoinMessage){
			return true;
		}
		return false;
	}

}
