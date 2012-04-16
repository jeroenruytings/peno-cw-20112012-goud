package communicator.parser.messages;


import communicator.parser.MessageType;

import data.world.World;

public class PongMessage extends Message {

	private String _name;
	private String _bestemmeling;
	private String _string;
	
	
	public PongMessage(String name, String bestemmeling, String string) {
		this._name = name;
		this._bestemmeling = bestemmeling;
		this._string = string;
	}

	@Override
	public String getNameFrom() {
		return _name;
	}
	
	/**
	 * @return The name of the recipient.
	 */
	public String getBestemmeling(){
		return _bestemmeling;
	}
	
	/**
	 * @return The string passed along to the pong commando.
	 */
	public String getString(){
		return _string;
	}

	@Override
	void execute(World simulator) {
		// TODO Hebben we zelf een ping uitgestuurt naar deze persoon?

	}

	@Override
	public MessageType getMessageType() {
		return MessageType.PONG;
	}
	
	@Override
	public boolean equals(Message pongMessage) {
		if (pongMessage instanceof PongMessage){
			PongMessage cmdBar = (PongMessage) pongMessage;
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
