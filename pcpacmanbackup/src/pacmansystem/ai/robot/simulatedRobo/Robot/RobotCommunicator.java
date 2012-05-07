package pacmansystem.ai.robot.simulatedRobo.Robot;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RobotCommunicator {
	private static RobotCommunicator instance;
	private DataOutputStream out;
	private DataInputStream in;

	
	public RobotCommunicator(DataInputStream in,DataOutputStream out) {
		this.in = in;
		this.out = out;
	}

	synchronized public void send(Message message) {
		byte[] messge = message.getEncodedMessage();
		this.send(messge[0]);
		this.send(messge[1]);
		
	}

	private void send(byte mes) {

		try {
			out.writeByte(mes);
		} catch (IOException e) {
			System.out.println("IO exception occured");
		}

	}


	public Commando receiveCommando() {
		Commando commando = null;
		try {
			int i = in.readInt();
			commando = CommandoListener.decodeCommando(i);
			return commando;
		} catch (IOException e) {
			System.out.println("fout gegaan in het lezen van de inputstream");
		}
		return commando;
		
	}
}
