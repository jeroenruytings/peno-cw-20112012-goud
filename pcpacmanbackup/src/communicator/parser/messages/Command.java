package communicator.parser.messages;


import data.world.World;

public class Command
{

	private final Message _myMessage;
	
	/**
	 * Create a new command for the given message.
	 * @param 	message
	 * 				The message to execute.
	 */
	public Command(Message message){
		_myMessage = message;
	}
	
	/**
	 * Execute this command on the given world.
	 * @param 	world
	 * 				The world to execute this command on.
	 */
	public void execute(World world){
		try{
			_myMessage.execute(world);
		}catch (MessageExecuteException e) {
			System.err.println("De volgende Message is niet uitgevoerd: " + _myMessage);
			System.err.println("Waarscheinlijk omdat de naam van de zendende robot niet gekent is.");
			System.err.println("Kijk in de 'canExecute(world)' methode van de corresponderende message-klasse voor meer info.");
		}
	}

	
	
	

}
