package communicator.parser.messages;

import data.world.World;

public class CapturedMessage extends Message
{


	public CapturedMessage(String nameFrom)
	{
		super(nameFrom);
	}

	@Override
	void execute(World simulator)
	{
		simulator.getRobot(getNameFrom()).setCapturedPacman(true);
	}

	@Override
	public String getKeyword() {
		return "CAPTURED";
	}
	
	@Override
	public boolean equals(Message cmd) {
		if (cmd instanceof CapturedMessage){
			CapturedMessage cmdBar = (CapturedMessage) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom()))
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
