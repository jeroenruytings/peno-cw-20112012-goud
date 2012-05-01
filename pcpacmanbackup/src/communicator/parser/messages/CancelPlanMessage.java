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
	public boolean equals(Message cmd) {
		if (cmd instanceof CancelPlanMessage){
			CancelPlanMessage cmdCnlPl = (CancelPlanMessage) cmd;
			if ((cmdCnlPl.getNameFrom() == this.getNameFrom()))
				return true;
		}
		return false;
	}
	
	

}
