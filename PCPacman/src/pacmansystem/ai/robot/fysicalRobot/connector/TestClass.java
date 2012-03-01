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
		
//		mover.getPcc().sendCommando(new Commando(Action.FORWARD, "BarcodeTest"));
		
		while (true){
			 
			int sonar = mover.getUltrasonic();
			int IRD = mover.getInfraredSensorDirection();
			int IRV = mover.getInfraRedSensorValue();
			int tacho = mover.getTachoCount();
			int light =  mover.getUltrasonic();
			
			sonar = mover.getUltrasonic();
			System.out.println("SONAR: " + sonar );
		
			light = mover.getLightSensor();
			System.out.println("LIGHT: " + light );
				
			IRD = mover.getInfraredSensorDirection();
			System.out.println("IRD:" + IRD );
			
			IRV = mover.getInfraRedSensorValue();
			System.out.println("IRV " + IRV);
			
			tacho = mover.getTachoCount();
			System.out.println("TACHO: " + tacho);
			}
		}
			
	}


