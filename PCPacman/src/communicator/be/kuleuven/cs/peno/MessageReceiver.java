package communicator.be.kuleuven.cs.peno;

import java.io.IOException;
import java.text.ParseException;

import util.world.RealWorld;
import util.world.World;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import communicator.parser.Command;
import communicator.parser.ProtocolDecoder;

public class MessageReceiver implements Runnable{
	
	private static final String MONITOR_KEY = "race.*";
	final private ProtocolDecoder decoder;
	final private Channel channel;
	final private Connection conn;
	final AMQP.Queue.DeclareOk queue;
	private World world;

	public static void main(String[] args) {
		try {
			MessageReceiver test = new MessageReceiver(new World());
			test.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MessageReceiver(World world) throws IOException{
		this.world = world;
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
					System.out.println(String.format("@%d: %s -> %s", //berichtnummer
							properties.getTimestamp().getTime(), //tijdstip van verzenden
							envelope.getRoutingKey(), //race.launch
							new String(body))); //boodschap
					
					String message = new String(body);
					System.out.println(message);
					try {
						Command command = decoder.parse(message);
						command.execute(world);
					} catch (ParseException e) {
						System.out.println("fail");
					}
					
					
					
					// send an ack to the server so it can remove the message from
					// the queue.	
					channel.basicAck(deliveryTag, false);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}