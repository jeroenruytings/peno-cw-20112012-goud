package communication;
import exceptions.ConnectionFailedException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;


public class Connection {
	
	private NXTConnector conn;
	
	/**
	 * @throws ConnectionFailedException
	 */
	public Connection(String name) {
		conn = new NXTConnector();
		boolean isConnected = conn.connectTo("btspp://"+name);
		if (!isConnected){
			throw new ConnectionFailedException();
		}
		System.out.println("yeey greqt succes");
		
	}
	
	public NXTConnector getConnection() {
		return conn;
	}
	
}
