package data.world;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import communicator.parser.Command;
import communicator.parser.MessageType;

public class ControlledRobotData extends RobotData {

	/**
	 * Map containing the messages that need to be acknowledged.
	 */
	Map<MessageType,Command> pendingMessages = new ConcurrentHashMap<MessageType, Command>(MessageType.values().length);
	
	/**
	 * This method processes the given message. It will only be processes if the message was issued by this RobotData.
	 * @param message
	 */
	final void processMessage(Command message){
		if (message.getNameFrom().equals(this.getName()))
			processSelfMessage(message);
		else
			executeMessage(message);
	}
	
	protected void executeMessage(Command message){
		//TODO: Execute message from an other robot.
		// Could be a discover command, translated to the axis of this robot.
	}
	
	protected void processSelfMessage(Command message){
		if (!pendingMessages.containsKey(message.getMessageType()))
			throw new IllegalArgumentException("The command was not issued by this RobotData");
		//TODO: 
	}
}
