package pacmansystem.ai.robot.fysicalRobot.connector;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.BarcodeReader;

public class TestClass {
	
	static MoverLayer mover;
	
	public TestClass(){
		
	}
	
	public static void main(String [] args){
		
		MoverLayer mover = new MoverLayer();
		
		System.out.println("Pcc: " + mover.getPcc());
		System.out.println("Virtu:" + mover.getPcc().getVirtu());	
		
	
			System.out.println("BarcodeReader BLACK: " + mover.getBarcodeReader().getBLACK());
			System.out.println("BarcodeReader WHITE: " + mover.getBarcodeReader().getWHITE());
			System.out.println("BarcodeReader BROWN: " + mover.getBarcodeReader().getBROWN());
		
		mover.getPcc().sendCommando(new Commando(Action.FORWARD, "BarcodeTest"));
		
		while (true){
			int barcode = mover.getBarcodeReader().getBarcode();
			if(barcode != 666){
			System.out.println();
			}
		}
			
	}

}
