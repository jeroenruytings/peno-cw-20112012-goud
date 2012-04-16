package data.world;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import communicator.parser.MessageType;
import communicator.parser.messages.Command;
import communicator.parser.messages.Message;

public class ControlledRobotData extends RobotData {

	/**
	 * Map containing the messages that need to be acknowledged.
	 */
	Map<MessageType,Command> pendingMessages = new ConcurrentHashMap<MessageType, Command>(MessageType.values().length);
	
	/**
	 * This method processes the given message. It will only be processes if the message was issued by this RobotData.
	 * @param message
	 */
	final void processMessage(Message message){
		if (message.getNameFrom().equals(this.getName()))
			processSelfMessage(message);
		else
			executeMessage(message);
	}
	
	protected void executeMessage(Message message){
		//TODO: Execute message from an other robot.
		// Could be a discover command, translated to the axis of this robot.
	}
	
	protected void processSelfMessage(Message message){
		if (!pendingMessages.containsKey(message.getMessageType()))
			throw new IllegalArgumentException("The command was not issued by this RobotData");
		//TODO: 
	}
}
