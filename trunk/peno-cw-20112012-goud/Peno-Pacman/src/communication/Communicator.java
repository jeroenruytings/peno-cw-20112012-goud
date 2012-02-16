package communication;

import java.io.DataOutputStream;
import java.io.IOException;


public class Communicator {
	
	private Connection connection;
	private DataOutputStream stream;
	
	public Communicator(){
		connection = new Connection("Goud");
		stream = connection.getConnection().getDataOut();
	}
	
	public void sendCommando(Commando commando) {
		try {
			stream.writeInt(commando.getAction().ordinal());
			stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
