package communicator.be.kuleuven.cs.peno;

import java.io.IOException;
import java.util.List;

import communicator.parser.messages.Message;
import communicator.parser.messages.PositionMessage;

public abstract class MessageRePublisher {
	
	
	public MessageRePublisher(){
		
	}
	
	/**
	 * This method will republish all the messages in the given list.
	 * It will also resent the last position-message.
	 * @param 	messages
	 * 				Messages to republish.
	 */
	public void rePublishMessage(List<Message> messages){
		PositionMessage lastPosition = null;
		for(Message msg : messages){
			if (msg instanceof PositionMessage)
				lastPosition = (PositionMessage) msg;
			rePublishMessage(msg);
		}
		try {
			MessageSender.getInstance().sendMessage(lastPosition);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Resent the given message.
	 * @param 	message
	 * 				The message to resent.
	 */
	public void rePublishMessage(Message message){
		try {
			MessageSender.getInstance().sendMessage(message.getShowMapMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
