package be.kuleuven.cs.peno;

import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


public class MessageSender{
	
	private static MessageSender instance;
	private Connection conn;
	private Channel channel;


	protected MessageSender() throws IOException{
			conn = MQ.createConnection();
			channel = MQ.createChannel(conn);
	}
		
	
	public static MessageSender getInstance() throws IOException{
		if(instance == null){
			instance = new MessageSender();
		}
		return instance;
	}
	
	public void sendMessage(String message) throws IOException{
		if (message != null) {
			AMQP.BasicProperties props = new AMQP.BasicProperties();
			props.setTimestamp(new Date());
			props.setContentType("text/plain");
			props.setDeliveryMode(1);
			
			channel.basicPublish(Config.EXCHANGE_NAME, Config.LAUNCH_ROUTING_KEY, props, message.getBytes());
			System.out.println(String.format("Send message '%s' to exchange '%s' with key '%s'",
					message, Config.EXCHANGE_NAME, Config.LAUNCH_ROUTING_KEY));
		}
	}
}
