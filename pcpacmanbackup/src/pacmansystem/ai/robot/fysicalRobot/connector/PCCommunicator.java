package pacmansystem.ai.robot.fysicalRobot.connector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pacmansystem.ai.robot.simulatedRobot.SimulationConnection;

public class PCCommunicator implements Runnable,Communicator
{

	private LeoMonitor startOfChain;
	private Connection connection;
	private DataOutputStream streamOut;
	private DataInputStream streamIn;
	private MoverLayer virtu;

	public PCCommunicator(MoverLayer virtu)
	{
		this.virtu = virtu;
		startOfChain = buildMonitors(virtu);

		try {
			connection = new Connection("Goud");
		} catch (ConnectionFailedException e) {
			e.printStackTrace();
		}
		streamOut = connection.getConnection().getDataOut();
		streamIn = connection.getConnection().getDataIn();
	}

//	public void start()
//	{
//		Thread t = new Thread();
//		t.start();
//	}

	public PCCommunicator(MoverLayer virtu,SimulationConnection sim)
	{
		this.virtu = virtu;
		startOfChain = buildMonitors(virtu);
		streamOut =new DataOutputStream(sim.getPcOut());
		streamIn = new DataInputStream(sim.getPCIN());
	}
	private LeoMonitor buildMonitors(MoverLayer mover)
	{
		return new SensorMonitor(new NullMonitor(null), mover);
	}

	public void sendCommando(Commando commando)
	{
		try {
			int k = (((commando.getAction().ordinal())*1000) + commando.getArgument());
			streamOut.writeInt(k);
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
				//TODO run invullen
				receiveValues();
//				System.out.println(virtu.buttonIsPushed());
//				Random generator = new Random();
//				Action action = Action.getActionByOrdinal(generator.nextInt(6));
//				Commando comm = new Commando(action, "");
//				sendCommando(comm);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("things went bananas QQ2!");
		}
	}

	public MoverLayer getVirtu() {
		return this.virtu;
	}

}
