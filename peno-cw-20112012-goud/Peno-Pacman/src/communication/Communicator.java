package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Communicator {
	
	private Connection connection;
	private DataOutputStream streamOut;
	private DataInputStream streamIn;
	
	public Communicator(){
		connection = new Connection("Goud");
		streamOut = connection.getConnection().getDataOut();
		streamIn = connection.getConnection().getDataIn();
	}
	
	public void sendCommando(Commando commando) {
		try {
			streamOut.writeInt(commando.getAction().ordinal());
			streamOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveValue(){
		try {
			System.out.println(streamIn.readInt());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public int[] receiveValue(){
//		streamIn.readUTF();
//	}
}
