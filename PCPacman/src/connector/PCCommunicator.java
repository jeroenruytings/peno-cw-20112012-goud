package connector;

import java.io.DataInputStream;

import java.io.DataOutputStream;

import lejos.pc.comm.NXTConnector;

public class PCCommunicator implements Runnable {

	private LeoMonitor startOfChain = buildMonitors();
	
	private Connection connection;
	private DataOutputStream streamOut;
	private DataInputStream streamIn;
	
	public PCCommunicator(){
		try {
			connection = new Connection("Goud");
		} catch (ConnectionFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		streamOut = connection.getConnection().getDataOut();
		streamIn = connection.getConnection().getDataIn();
	}
	
	 public static void main(String[] args) {
		 PCCommunicator comm = new PCCommunicator();
			Thread t = new Thread(comm);
			t.start();
	 }
	
	private static LeoMonitor buildMonitors() {
		return new SensorMonitor(
								new NullMonitor(null)
				);
	}

	@Override
	public void run() {
		try {
			while (true) {
				byte[] input = new byte[2];		
				input[0] = streamIn.readByte();
				input[1] = streamIn.readByte();
				startOfChain.accept(input);

			}
		} catch (Exception e) {
			System.out.println("things went bananas QQ!");
		}
	}

}

