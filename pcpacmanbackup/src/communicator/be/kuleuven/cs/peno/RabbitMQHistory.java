package communicator.be.kuleuven.cs.peno;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import communicator.parser.messages.Message;

public class RabbitMQHistory implements Observer{

	private List<Message> sendMessages;
	private List<Message> receivedMessages;

	private MessageReceiver messageReceiver;
	
	public RabbitMQHistory(MessageReceiver messageReceiver){
		sendMessages = new ArrayList<Message>();
		receivedMessages = new ArrayList<Message>();
		this.messageReceiver = messageReceiver; 
		MessageSender.getInstance().addObserver(this);
		messageReceiver.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object message) {
		if (arg0 == MessageSender.getInstance()){
			sendMessages.add((Message) message);	
		}
		else if (arg0 == messageReceiver){
			receivedMessages.add((Message) message);
		}
	}
	
	/**
	 * Get a list of messages received from the robot with the given name.
	 * @param 	name
	 * 				Name of the robot.
	 * 				If null is passed as parameter, this method will return the messages received from all the robots.
	 * @return
	 * 			A List of messages received from the robot with the given name.
	 */
	public List<Message> getReceivedMessagesFrom(String name){
		if(name==null)
			return new ArrayList<Message>(receivedMessages);
		List<Message> result = new ArrayList<Message>();
		for(Message msg : receivedMessages)
			if (msg.getNameFrom().equals(name))
				result.add(msg);
		return result;
	}
	
	/**
	 * @return Get a list of all send messages.
	 */
	public List<Message> getSendMessages(){
		return new ArrayList<Message>(sendMessages);
	}
}
