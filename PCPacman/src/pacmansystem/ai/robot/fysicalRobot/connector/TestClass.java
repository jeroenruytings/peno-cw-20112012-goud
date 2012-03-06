package pacmansystem.ai.robot.fysicalRobot.connector;

import java.io.IOException;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.DirectionLayer;
import pacmansystem.ai.robot.PanelLayer;
import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.board.Panel;
import pacmansystem.board.enums.Direction;
import pacmansystem.board.enums.Orientation;
import pacmansystem.world.RealWorld;

public class TestClass {
	
	static MoverLayer mover;
	
	public TestClass(){
		
	}
	
	public static void main(String [] args){
		
		MoverLayer mover = new MoverLayer();
		
		PanelLayer panel = new PanelLayer(mover);
		
		System.out.println("Pcc: " + panel.getMover().getPcc());
		System.out.println("Virtu:" + panel.getMover().getPcc().getVirtu());	
		
	
		System.out.println("BarcodeReader BLACK: " + panel.getMover().getColorStack().getColor(PanelColor.BLACK));
		System.out.println("BarcodeReader WHITE: " + panel.getMover().getColorStack().getColor(PanelColor.WHITE));
		System.out.println("BarcodeReader BROWN: " + panel.getMover().getColorStack().getColor(PanelColor.BROWN));
		
			
		System.out.println("Up: " + panel.hasBorder(Direction.UP));
		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));


		
		
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(panel.getBarcode().getValue());
			
//			for (int i = 0 ; i<50000; i++);
//			mover.drive(100);
//			mover.drive(100);
//			mover.drive(100);
//			mover.drive(100);
//			mover.drive(100);
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			
//		}
//		Barcode c=		mover.getBarcodeReader().searchForCode();
//		System.out.println(c.getValue());
//		mover.turn(-90);
//		mover.drive(-100);
//		mover.turn(90);
//		mover.turnHead(-90);
//		mover.turnHead(180);
//		mover.turnHead(-90);
//		
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


