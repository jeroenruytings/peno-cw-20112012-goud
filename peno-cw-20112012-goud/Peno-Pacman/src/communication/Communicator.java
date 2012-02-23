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
	
public int [] receiveValues() throws Exception{
		
		int numberOfBytes = -1;
		
		try {
			numberOfBytes = streamIn.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (numberOfBytes == -1){
			throw new Exception("no input available");
		}
		else{
			byte[] data = new byte [numberOfBytes];
			streamIn.read(data, 0, numberOfBytes);
			String dataString = data.toString();
			String[] sensorValues = dataString.split(":");
			
			return stringToInt(sensorValues);
		}
		
	}

	private int[] stringToInt(String[] stringValues) {
		int [] integers = new int[stringValues.length];
		for (int i = 0 ; i<stringValues.length ; i++){
			integers [i] = Integer.parseInt(stringValues[i]);
			System.out.println(integers[i]);
		}
		return integers;
	}
}
