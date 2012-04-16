package communicator.parser.messages;




import data.world.World;

public class CancelPlanMessage extends Message
{

	public CancelPlanMessage(String nameFrom)
	{
		super(nameFrom);
	}

	@Override
	void execute(World simulator)
	{
		simulator.getRobot(getNameFrom()).clearPlan();
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
