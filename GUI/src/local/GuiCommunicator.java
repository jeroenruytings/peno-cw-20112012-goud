package local;

import java.io.DataInputStream;

import lejos.pc.comm.NXTConnector;

public class GuiCommunicator implements Runnable {
	private DataInputStream in;
	private LeoMonitor startOfChain = buildMonitors();

	public GuiCommunicator() {
		// XXX: do not delete this code
		 NXTConnector conn = new NXTConnector();
		
		 boolean connected = conn.connectTo("btspp://");
		
		 if (!connected) {
		 System.err.println("Failed to connect to any NXT");
		 System.exit(1);
		 }
		 System.out.println("Connected!");
		
		 in = conn.getDataIn();
		System.out.println("getData");

	}
	private static LeoMonitor buildMonitors() {
		return new SensorMonitor(
				new ActionLogMonitor(
						new BehaviourMonitor(
								new NullMonitor(null)
								)
						)
				);
	}

	@Override
	public void run() {
		try {
			while (true) {
				byte[] input = new byte[2];
				input[0] = in.readByte();
				input[1] = in.readByte();
				startOfChain.accept(input);
				
			}
		} catch (Exception e) {
			System.out.println("things went bananas QQ!");
		}
	}

}
