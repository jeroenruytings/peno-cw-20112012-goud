package pacmansystem.ai.robot.fysicalRobot.connector;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;

public class TestClass {
	
	static MoverLayer mover;
	
	public TestClass(){
		
	}
	
	public static void main(String [] args){
		
		MoverLayer mover = new MoverLayer();
		
		System.out.println("Pcc: " + mover.getPcc());
		System.out.println("Virtu:" + mover.getPcc().getVirtu());	
		
	
			System.out.println("BarcodeReader BLACK: " + mover.getBarcodeReader().getColor(PanelColor.BLACK));
			System.out.println("BarcodeReader WHITE: " + mover.getBarcodeReader().getColor(PanelColor.WHITE));
			System.out.println("BarcodeReader BROWN: " + mover.getBarcodeReader().getColor(PanelColor.BROWN));
		

		mover.drive(100);
		mover.turn(-90);
		mover.drive(-100);
		mover.turn(90);
		mover.turnHead(-90);
		mover.turnHead(180);
		mover.turnHead(-90);
			
//		mover.getPcc().sendCommando(new Commando(Action.FORWARD, "BarcodeTest"));
		
//		while (true){
//			 
//			int sonar = mover.getUltrasonic();
//			int IRD = mover.getInfraredSensorDirection();
//			int IRV = mover.getInfraRedSensorValue();
//			int tacho = mover.getTachoCount();
//			int light =  mover.getUltrasonic();
//			
//			sonar = mover.getUltrasonic();
//			System.out.println("SONAR: " + sonar );
//		
//			light = mover.getLightSensor();
//			System.out.println("LIGHT: " + light );
//				
//			IRD = mover.getInfraredSensorDirection();
//			System.out.println("IRD:" + IRD );
//			
//			IRV = mover.getInfraRedSensorValue();
//			System.out.println("IRV " + IRV);
//			
//			tacho = mover.getTachoCount();
//			System.out.println("TACHO: " + tacho);
//			}
		}
			
	}


