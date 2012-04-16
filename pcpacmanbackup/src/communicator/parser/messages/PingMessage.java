package communicator.parser.messages;


import communicator.parser.MessageType;

import data.world.RobotData;
import data.world.World;

public class PingMessage extends Message {

	private String _name;
	private String _bestemmeling;
	private String _string;
	
	public PingMessage(String name, String bestemmeling, String string){
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
	void execute(World simulator) {
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

	@Override
	public boolean correctMessage() {
		// TODO Auto-generated method stub
		return false;
	}

}
