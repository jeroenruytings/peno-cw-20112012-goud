 
//import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class DataPasser implements Runnable{

	private DataInputStream dis;
	private DataOutputStream dos;
	
	public DataPasser(){
		BTConnection btc = Bluetooth.waitForConnection();
		
		dis = btc.openDataInputStream();
		dos = btc.openDataOutputStream();
	}
	
	public void waitForCommand() throws IOException{
		//BufferedReader bdis = new BufferedReader(new InputStreamReader(dis));
		//dis.readUTF()
		
		
		//String command = dis.readUTF();
		int command = dis.readInt();
		
		if (command == 1)//command.equals("barcode"))
		{
			dos.writeInt(MainController.one.getBarcode());
		}
		else if(command == 2){//command.equals("muurwaarde")){
			dos.writeInt(MainController.two.getDistance());
		}
		else if(command == 3){//command.equals("lijnwaarde"));
			dos.writeInt(MainController.three.getValue());
		}

		dos.flush();
	}

	
	public void run() {
		while(true){
			try {
				waitForCommand();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
		}
	}

}
