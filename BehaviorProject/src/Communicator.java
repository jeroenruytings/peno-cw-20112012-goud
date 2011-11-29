
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Communicator {
	private static Communicator instance;
	private DataOutputStream out;
	@SuppressWarnings("unused")
	private DataInputStream in;

	public static Communicator instance() {
		if (instance == null)
			instance = new Communicator();
		return instance;
	}

	private Communicator() {
		BTConnection con = Bluetooth.waitForConnection();
		in = con.openDataInputStream();
		out = con.openDataOutputStream();
	}

	public void send(Message message) {
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
