

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.SensorPort;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class NXTCommunicator implements Runnable{
	
	private DataInputStream dis;
	private DataOutputStream dos;

	public NXTCommunicator(){
		System.out.println("Waiting");
		BTConnection btc = Bluetooth.waitForConnection();
		System.out.println("Bluetooth");
		dis = btc.openDataInputStream();
		dos = btc.openDataOutputStream();
	}
	
	private void waitForCommand() throws IOException{
		
		int command = dis.readInt(); //read command from computer
		
		System.out.println("command: "+command);
		
		if (command == 100)//lees lichtsensor
		{
			dos.writeInt(SensorPort.S1.readRawValue());
		}
		else if(command == 200){//lees sonar
			dos.writeInt(SensorPort.S3.readRawValue());
		}
		else if(command == 300){//lees ir;
			dos.writeInt(SensorPort.S4.readRawValue());
		}
		else{//commando uitvoeren, niets terugsturen
			currentCommand = command;
		}
		
		dos.flush();
	}

	public int currentCommand;
	
	
	public int getCurrentCommand() {
		return currentCommand;
	}

	public void setCurrentCommand(int currentCommand) {
		this.currentCommand = currentCommand;
	}

	public void run() {
		while(true){

				try {
					waitForCommand();
					dos.writeInt(SensorPort.S3.readRawValue());
					dos.flush();
				} catch (IOException e) {
					System.out.println("error!");
				}
		}
	}
	
}
