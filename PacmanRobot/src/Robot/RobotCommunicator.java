package Robot;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class RobotCommunicator {
	private static RobotCommunicator instance;
	private DataOutputStream out;
	@SuppressWarnings("unused")
	private DataInputStream in;

	public static RobotCommunicator instance() {
		if (instance == null)
			instance = new RobotCommunicator();
		return instance;
	}

	private RobotCommunicator() {
		System.out.println("waiting..");
		BTConnection con = Bluetooth.waitForConnection();
		System.out.println("connection established");
		in = con.openDataInputStream();
		out = con.openDataOutputStream();
	}

	synchronized public void send(Message message) {
		byte[] messge = message.getEncodedMessage();
		this.send(messge[0]);
		this.send(messge[1]);
		try {
			out.flush();
		} catch (IOException e) {
			System.out.println("Can't flush!");
		}
	}

	private void send(byte mes) {
		try {
			out.write(mes);
		} catch (IOException e) {
			System.out.println("IO exception occured");
		}

	}
}
