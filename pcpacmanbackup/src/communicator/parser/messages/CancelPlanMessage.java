package communicator.parser.messages;


import communicator.parser.MessageType;

import data.world.World;

public class CancelPlanMessage extends Message
{

	private String _name;

	public CancelPlanMessage(String _name)
	{
		this._name = _name;
	}

	@Override
	public String getNameFrom()
	{
		return _name;
	}

	@Override
	void execute(World simulator)
	{
		simulator.getRobot(_name).clearPlan();
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.CANCELPLAN;
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
	
	

}
