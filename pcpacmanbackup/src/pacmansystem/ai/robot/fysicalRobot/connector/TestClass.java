package pacmansystem.ai.robot.fysicalRobot.connector;

import pacmansystem.ai.robot.fysicalRobot.PanelColor;
import pacmansystem.ai.robot.fysicalRobot.PanelLayer;
import data.board.Panel;
import data.enums.Direction;
import data.enums.Orientation;



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
		
		
		Panel paneel = panel.getPanel(Orientation.NORTH);
		panel.go(Direction.UP);
		paneel = panel.getPanel(Orientation.NORTH);
		
		
//		for(int i = 0 ; i<5 ; i++){
//		System.out.println("now up");
//		panel.go(Direction.UP);
//		panel.correctToMiddle();
//		}
//		panel.go(Direction.LEFT);
//		panel.correctToMiddle();
//		panel.go(Direction.UP);
//		panel.correctToMiddle();
//		panel.go(Direction.UP);
//		panel.correctToMiddle();
		
		
		
//		System.out.println(paneel.getBarcode().getBitString2());
		
//		//	panel.getPanel(Orientation.NORTH);
//		//while (true){
//			panel.correctToMiddle();
//			//System.out.println("pacman found: " + panel.getPacman());
//		//}
		
		
//		// TESTDOOLHOF
//		System.out.println("Op Paneel 0");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		Panel paneel = panel.getPanel(Orientation.NORTH);
//	//	System.out.println(panel.getBarcode().getValue());
//		System.out.println(paneel.bordersToString());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.UP);
//		
//		System.out.println("Op Paneel 1");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.NORTH);
////		System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.UP);
//		
//		System.out.println("Op Paneel 2");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.NORTH);
//	//	System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.UP);
//		
//		System.out.println("Op Paneel 3");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.NORTH);
//	//	System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.LEFT);
//		
//		System.out.println("Op Paneel 4");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.WEST);
////		System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.UP);
//		
//		System.out.println("Op Paneel 5");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.WEST);
//	//	System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.UP);
//		
//		System.out.println("Op Paneel 6");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.WEST);
//	//	System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.LEFT);
//		
//		System.out.println("Op Paneel 7");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.SOUTH);
//	//	System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.LEFT);
//		
//		System.out.println("Op Paneel 8");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.EAST);
//	//	System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.UP);
//		
//		System.out.println("Op Paneel 9");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.EAST);
//	//	System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.RIGHT);
//		
//		System.out.println("Op Paneel 10");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.SOUTH);
////		System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.UP);
//		
//		System.out.println("Op Paneel 11");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.SOUTH);
////		System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.RIGHT);
//		
//		System.out.println("Op Paneel 12");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.WEST);
////		System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.RIGHT);
//		
//		System.out.println("Op Paneel 13");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.NORTH);
////		System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.LEFT);
//		
//		System.out.println("Op Paneel 14");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.WEST);
////		System.out.println(panel.getBarcode().getValue());
//		System.out.println("");
//		System.out.println("Ga naar volgende paneel");
//		System.out.println("");
//		panel.go(Direction.LEFT);
//		
//		System.out.println("Op Paneel 15");
////		System.out.println("Up: " + panel.hasBorder(Direction.UP));
////		System.out.println("Right: " + panel.hasBorder(Direction.RIGHT));
////		System.out.println("Down: " + panel.hasBorder(Direction.DOWN));
////		System.out.println("Left: " + panel.hasBorder(Direction.LEFT));
//		panel.getPanel(Orientation.SOUTH);
////		System.out.println(panel.getBarcode().getValue());
////		System.out.println("");
////		System.out.println("Ga naar volgende paneel");
////		System.out.println("");
////		panel.go(Direction.RIGHT);



		
		
//			try {
//				System.in.read();
//			} catch (IOException e) {
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


