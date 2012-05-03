package communicator.parser.messages;




import data.world.World;

public class PongMessage extends Message {

	private String _bestemmeling;
	private String _string;
	
	
	public PongMessage(String nameFrom, String bestemmeling, String string) {
		super(nameFrom);
		setParameter(bestemmeling);
		setParameter(string);
		this._bestemmeling = bestemmeling;
		this._string = string;
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
	public boolean canExecute(World world){
		return true;
	}
	
	@Override
	void execute(World world) {
		if (!canExecute(world))
			throw new MessageExecuteException();
		// TODO Hebben we zelf een ping uitgestuurt naar deze persoon?

	}

	@Override
	public String getKeyword() {
		return "PONG";
	}

	@Override
	protected boolean equalParameters(Message pongMessage) {
		if (pongMessage instanceof PongMessage){
			PongMessage cmdBar = (PongMessage) pongMessage;
			if (cmdBar.getBestemmeling().equals(this.getBestemmeling())
					&& (cmdBar.getString().equals(this.getString())))
				return true;
		}
		return false;
	}

}
