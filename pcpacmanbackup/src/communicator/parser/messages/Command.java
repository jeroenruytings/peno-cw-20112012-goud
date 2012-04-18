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
		_myMessage.execute(world);
	}

	
	
	

}
