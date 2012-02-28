package world;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class MQCommunicator implements Runnable {
	
	private static final String QUEUENAME = "queue";
	private Connection _conn;
	private Channel _chan;
	public MQCommunicator(String host) throws IOException{
		ConnectionFactory fact = new ConnectionFactory();
		fact.setHost(host);
		_conn = fact.newConnection();
		_chan = _conn.createChannel();
		
	}

	@Override
	public void run() {
		QueueingConsumer consumer = new QueueingConsumer(_chan);
		try {
			_chan.basicConsume(QUEUENAME,true,consumer);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true)
		{
			Delivery del = null;
			try {
				del = consumer.nextDelivery();
			} catch (ShutdownSignalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConsumerCancelledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String message = new String(del.getBody());
			
			
		}
	}

}
