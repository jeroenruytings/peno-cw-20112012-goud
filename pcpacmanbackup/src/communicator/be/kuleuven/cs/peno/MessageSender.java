package communicator.be.kuleuven.cs.peno;

import interfaces.pacmancomponents.RabbitHistory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Observable;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import communicator.parser.decoder.ProtocolDecoder;
import communicator.parser.messages.Message;


public class MessageSender extends Observable{
	
	private static MessageSender instance;
	private Connection conn;
	private Channel channel;
	private ProtocolDecoder decoder = new ProtocolDecoder();


	private MessageSender() throws IOException{
			conn = MQ.createConnection();
			channel = MQ.createChannel(conn);
	}
		
	
	public static MessageSender getInstance(){
		if(instance == null){
			try {
				instance = new MessageSender();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public void sendMessage(Message message) throws IOException{
		sendMessage(message.getSentString());
		notifyObservers(message);
	}
	
	//TODO maak deze methode private.
	public void sendMessage(String message) throws IOException{
		if (message != null) {
			AMQP.BasicProperties props = new AMQP.BasicProperties();
			props.setTimestamp(new Date());
			props.setContentType("text/plain");
			props.setDeliveryMode(1);
			
			channel.basicPublish(Config.EXCHANGE_NAME, Config.LAUNCH_ROUTING_KEY, props, message.getBytes());
//			System.out.println(String.format("Send message '%s' to exchange '%s' with key '%s'",
//					message, Config.EXCHANGE_NAME, Config.LAUNCH_ROUTING_KEY));
			try {
				RabbitHistory.messageSend(message, decoder.parse(message).getNameFrom());
			} catch (ParseException e) {
				e.printStackTrace();
				System.err.println("\n Het berich dat wordt uitgezonden is geen correct bericht.");
			}
		}
	}
}
