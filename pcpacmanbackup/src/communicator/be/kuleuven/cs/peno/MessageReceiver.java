package communicator.be.kuleuven.cs.peno;

import interfaces.pacmancomponents.RabbitHistory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Observable;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import communicator.parser.decoder.ProtocolDecoder;
import communicator.parser.messages.Command;
import communicator.parser.messages.Message;

public class MessageReceiver extends Observable implements Runnable{
	
	private static final String MONITOR_KEY = "race.*";
	final private ProtocolDecoder decoder;
	final private Channel channel;
	final private Connection conn;
	final AMQP.Queue.DeclareOk queue;

	private Command command;
	public static void main(String[] args) {
		try {
			MessageReceiver test = new MessageReceiver();
			Thread t = new Thread(test);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MessageReceiver() throws IOException{
		decoder = new ProtocolDecoder();
		conn = MQ.createConnection();
		channel = MQ.createChannel(conn);
		queue = channel.queueDeclare();
		channel.queueBind(queue.getQueue(), Config.EXCHANGE_NAME, MONITOR_KEY);
	}

	@Override
	public void run() {
		boolean noAck = false;
		try {
			channel.basicConsume(queue.getQueue(), noAck, new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, 
						AMQP.BasicProperties properties, byte[] body) throws IOException {
					// get the delivery tag to ack that we processed the message successfully
					long deliveryTag = envelope.getDeliveryTag();

					// properties.getTimestamp() contains the timestamp
					// that the sender added when the message was published. This 
					// time is the time on the sender and NOT the time on the 
					// AMQP server. This implies that clients are possibly out of
					// sync!
//					System.out.println(String.format("@%d: %s -> %s", //berichtnummer
//							properties.getTimestamp().getTime(), //tijdstip van verzenden
//							envelope.getRoutingKey(), //race.launch
//							new String(body))); //boodschap
					
					String message = new String(body);
					
					try {
						Message decodedMessage = decoder.parse(message);
						RabbitHistory.receiveMessage(message,decodedMessage.getNameFrom());

						setChanged();
						notifyObservers(decodedMessage);

					} catch (ParseException e) {
						System.out.println("fail");
					}
					
					
					
					// send an ack to the server so it can remove the message from
					// the queue.	
					channel.basicAck(deliveryTag, false);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	protected void setCommand(Command command) {
		this.command = command;
		System.out.println(command);
		setChanged();
		notifyObservers(command);
	}

	public Command getCommand() {
		return command;
	}
	
}
