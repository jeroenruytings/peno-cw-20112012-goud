package communicator.parser.command;


import communicator.parser.Command;
import communicator.parser.MessageType;
import data.world.RobotData;
import data.world.World;

public class CommandPing implements Command {

	private String _name;
	private String _bestemmeling;
	private String _string;
	
	public CommandPing(String name, String bestemmeling, String string){
		this._name = name;
		this._bestemmeling = bestemmeling;
		this._string = string;
	}
	
	@Override
	public String getNameFrom() {
		return _name;
	}
	
	public String getBestemmeling(){
		return _bestemmeling;
	}
	
	public String getString(){
		return _string;
	}

	@Override
	public void execute(World simulator) {
		for(RobotData robot : simulator.get_robots().values()){
			if(getBestemmeling().equals(robot.getName())||getBestemmeling().equals("*"))
				robot.pong(getNameFrom(),getString());
		}

	}

	@Override
	public MessageType getMessageType() {
		return MessageType.PING;
	}
	
	@Override
	public boolean equals(Command cmd) {
		if (cmd instanceof CommandPing){
			CommandPing cmdBar = (CommandPing) cmd;
			if ((cmdBar.getNameFrom() == this.getNameFrom())
					&& (cmdBar.getBestemmeling().equals(this.getBestemmeling()))
					&& (cmdBar.getString().equals(this.getString())))
				return true;
		}
		return false;
	}

}
