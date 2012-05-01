package communicator.parser.messages;

import data.world.RobotData;
import data.world.World;

public class PingMessage extends Message {

	private String _bestemmeling;
	private String _string;
	
	public PingMessage(String nameFrom, String bestemmeling, String string){
		super(nameFrom);
		setParameter(bestemmeling);
		setParameter(string);
		this._bestemmeling = bestemmeling;
		this._string = string;
	}
	
	public String getBestemmeling(){
		return _bestemmeling;
	}
	
	public String getString(){
		return _string;
	}

	@Override
	public boolean canExecute(World world){
		return true;
	}
	
	@Override
	void execute(World world) {
		if (!canExecute(world))
			throw new MessageExecuteException();
		for(RobotData robot : world.get_robots().values()){
			if(getBestemmeling().equals(robot.getName())||getBestemmeling().equals("*"))
				robot.pong(getNameFrom(),getString());
		}

	}

	@Override
	public String getKeyword() {
		return "PING";
	}
	
	@Override
	public boolean equals(Message pingMessage) {
		if (pingMessage instanceof PingMessage){
			PingMessage cmdBar = (PingMessage) pingMessage;
			if ((cmdBar.getNameFrom() == this.getNameFrom())
					&& (cmdBar.getBestemmeling().equals(this.getBestemmeling()))
					&& (cmdBar.getString().equals(this.getString())))
				return true;
		}
		return false;
	}

}
