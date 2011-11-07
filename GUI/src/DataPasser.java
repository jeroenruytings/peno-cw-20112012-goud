import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.pc.comm.NXTConnector;


public class DataPasser{
	static String barcode = "Barcode";
	static String lijn = "lijn";
	static String muur = "muur";
	int barcodeWaarde;
	int lijnWaarde;
	int muurWaarde;
	
	private NXTConnector conn = null;
	private DataOutputStream dos;
	private DataInputStream dis;
	
	DataPasser(){
		NXTConnector conn = new NXTConnector();
		
		boolean connected = conn.connectTo("btspp://");
		
		if (!connected) {
			System.err.println("Failed to connect to any NXT");
			System.exit(1);
		}
		System.out.println("Connected!");
		
		dos = conn.getDataOut();
		dis = conn.getDataIn();
//		System.out.println(dos.toString());
//		System.out.println(dis.toString());
		System.out.println("getData");
	}
	
//	DataPasser(int waarde, String string){
//		NXTConnector conn = new NXTConnector();
//		dos = conn.getDataOut();
//		dis = conn.getDataIn();
//				
//		if(string.equals(barcode))
//			setBarcodeWaarde(waarde);
//		if(string.equals(lijn))
//			setLijnWaarde(waarde);
//		if(string.equals(muur))
//			setMuurWaarde(waarde);
//	}

	public int getBarcodeWaarde() throws IOException{
//		System.out.println("ok");
//		//BufferedReader d = new BufferedReader(new InputStreamReader(dis));
//		System.out.println("ok2");
//		dos.writeChars("barcode");
//		dos.flush();
//		//dis.notify();
//		System.out.println("ok3");
//		System.out.println(dis.toString());
//		System.out.println(dos.toString());
//		int barcodeWaarde = dis.readInt();
//		System.out.println(barcodeWaarde);
//		return barcodeWaarde;
//		return 0;
		
		dos.writeInt(1);
		dos.flush();
		//dis.notify();
//		int muurWaarde = dis.readInt();
//		System.out.println(muurWaarde);
		int barcode = dis.readInt();
		return barcode;
	}

//	public void setBarcodeWaarde(int barcodeWaarde) {
//		this.barcodeWaarde = barcodeWaarde;
//	}

	public int getLijnWaarde() throws IOException {
		dos.writeInt(3);
		dos.flush();
		//dis.notify();
		//int muurWaarde = dis.readInt();
		int value = dis.readInt();
		return value;
		
		
		//System.out.println("line1");
		//return 0;
//		dos.writeChars("lijnwaarde");
		//dis.notify();
//		System.out.println("line2");
//		if(dis != null){
//			System.out.println("4");
//			lijnWaarde = dis.readInt();
//		}
//		System.out.println("line3");
//		return lijnWaarde;
	}
//
//	public void setLijnWaarde(int lijnWaarde) {
//		this.lijnWaarde = lijnWaarde;
//	}

	public int getMuurWaarde() throws IOException {
		dos.writeInt(2);
		dos.flush();
		//dis.notify();
		int muurWaarde = dis.readInt();
		return muurWaarde;
	}

//	public void setMuurWaarde(int muurWaarde) {
//		this.muurWaarde = muurWaarde;
//	}
	
	public void terminate(){
		try {
			dis.close();
			dos.close();
			conn.close();
		} catch (IOException e) {
			System.out.println("IOException closing connection:");
			System.out.println(e.getMessage());
		}
	}
}