package communicator.parser.messages;

import data.world.World;

public class CapturedMessage extends Message
{


	public CapturedMessage(String nameFrom)
	{
		super(nameFrom);
	}

	@Override
	void execute(World world)
	{
		if(!canExecute(world))
			throw new MessageExecuteException();
		world.getRobot(getNameFrom()).setCapturedPacman(true);
	}

	@Override
	public String getKeyword() {
		return "CAPTURED";
	}

	@Override
	protected boolean equalParameters(Message cmd) {
		if (cmd instanceof CapturedMessage)
			return true;
		return false;
	}


}
