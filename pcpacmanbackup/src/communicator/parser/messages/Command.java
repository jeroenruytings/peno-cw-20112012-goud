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
		if(!message.correctMessage())
			throw new IllegalArgumentException("The given message is not valid");
		_myMessage = message;
	}
	
	/**
	 * Execute this command on the given world.
	 * @param 	world
	 * 				The world to execute this command on.
	 */
	public void execute(World world){
		_myMessage.execute(world);
	}

	
	
	

}
