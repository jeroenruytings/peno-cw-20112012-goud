package pacmansystem.ai.robot.simulatedRobot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;


public class SimulationConnection
{
	private QueuedStream out;
	private QueuedStream in;
	public SimulationConnection()
	{
		 in = new QueuedStream();
		 out = new QueuedStream();
		
	}

	public OutputStream getOut()
	{
		return out.getOut();
	}

	public InputStream getIn()
	{
		return in.getIn();
	}

	
	public static void main(String[] args)
	{
		final SimulationConnection c = new SimulationConnection();
		
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				while(true)
					try {
						OutputStream stream = c.getOut();
						DataOutputStream data = new DataOutputStream(stream);
						data.writeUTF(new Scanner(System.in).next());
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}).start();
		final	OwnSimulatedConnection s = c.getOwn();
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				while(true)
					try {
						DataInputStream d = new DataInputStream(s.getIn());
						System.out.println(d.readUTF());
						
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}).start();
	}

	public OwnSimulatedConnection getOwn()
	{
		
		return new OwnSimulatedConnection(out, in);
	}

}
