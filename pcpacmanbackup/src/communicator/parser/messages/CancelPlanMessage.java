package communicator.parser.messages;




import data.world.World;

public class CancelPlanMessage extends Message
{
	
	public CancelPlanMessage(String nameFrom)
	{
		super(nameFrom);
	}

	@Override
	void execute(World world)
	{
		if(!canExecute(world))
			throw new MessageExecuteException();
		world.getRobot(getNameFrom()).clearPlan();
		synchronized (world.getRobot(getNameFrom())) {
			world.getRobot(getNameFrom()).notify();
		}
	}

	@Override
	public String getKeyword() {
		return "CANCELPLAN";
	}

	@Override
	protected boolean equalParameters(Message cmd) {
		if (cmd instanceof CancelPlanMessage)
			return true;
		return false;
	}
	
	

}
