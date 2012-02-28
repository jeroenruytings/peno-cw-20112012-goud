package pacmansystem.ai.robot.fysicalRobot.connector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class PCCommunicator implements Runnable
{

	private LeoMonitor startOfChain = buildMonitors();
	private Connection connection;
	private DataOutputStream streamOut;
	private DataInputStream streamIn;
	private VirtuBot virtu;

	public PCCommunicator()
	{
		try {
			connection = new Connection("Goud");
		} catch (ConnectionFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		streamOut = connection.getConnection().getDataOut();
		streamIn = connection.getConnection().getDataIn();
	}

	public void start(VirtuBot virtu)
	{
		PCCommunicator comm = new PCCommunicator();
		Thread t = new Thread(comm);
		this.virtu = virtu;
		t.start();
	}

	private LeoMonitor buildMonitors()
	{
		return new SensorMonitor(new NullMonitor(null), virtu);
	}

	public void sendCommando(Commando commando)
	{
		try {
			streamOut.writeInt(commando.getAction().ordinal());
			streamOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receiveValues() throws IOException
	{
		byte[] input = new byte[2];
		input[0] = streamIn.readByte();
		input[1] = streamIn.readByte();
		startOfChain.accept(input);
	}

	@Override
	public void run()
	{
		try {
			while (true) {
				receiveValues();
				Random generator = new Random();
				Action action = Action.getActionByOrdinal(generator.nextInt(6));
				Commando comm = new Commando(action, "");
				sendCommando(comm);
			}

		} catch (Exception e) {
			System.out.println("things went bananas QQ!");
		}
	}

}
