import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class LightSensorBehavior extends LeoBehavior {

	boolean suppressed = true;
	int lastSmallLine = 0;


	@Override
	public void action() {
		suppressed = false;
		barcodeReader.setSuppressed(false);
		pilot.setSpeed(360); //normaal 360
		pilot.forward();
		try{
		barcodeReader.run();
		}
		catch (Exception e){
			System.out.println("fout in barcode");
			pilot.stop();
	//		Button.ENTER.waitForPressAndRelease();
			barcodeReader = new BarcodeReader();
			System.out.println("nieuwe reader aangemaakt");
	//		Button.ENTER.waitForPressAndRelease();
			barcodeReader.setLastRead(BarcodeReader.barcode.a);
		}
		pilot.stop();
		while (!suppressed && pilot.isMoving()) {
			Thread.yield();
		}
		pilot.setSpeed(720);//
	//	BarcodeBehavior.setTakeControleTrue();
		
		// links is positief roteren
		try {
			switch (barcodeReader.lastRead()) {
			case six: //bocht rechts
				pilot.travel(250,true);
				interruptTravel();
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				double dist = barcodeReader.getTotaldist();
				int degrees = (int) (dist-290)*(90-45)/(446-290);
				System.out.println(degrees);
				if(lastSmallLine==0)//default geen lijn
					pilot.rotate(-90);
				if(lastSmallLine==1){//zwarte lijn
					pilot.rotate(-(90 - degrees));
					System.out.println(-(90 - degrees));
					}
				if(lastSmallLine==2){//witte lijn
					pilot.rotate(-(90 + degrees));
					System.out.println(-(90 + degrees));
				}
				
				
				//pilot.rotate(-80);
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				break;
			case three: //bocht links
				pilot.travel(250, true);
				interruptTravel();
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				double dist2 = barcodeReader.getTotaldist();
				int degrees2 = (int) (290-dist2)*(90-45)/(446-290);
				System.out.println(degrees2);
				if(lastSmallLine==0)//default geen lijn
					pilot.rotate(90);
				if(lastSmallLine==1){//zwarte lijn
					pilot.rotate(90 - degrees2);
					System.out.println(90 - degrees2);
					}
				if(lastSmallLine==2){//witte lijn
					pilot.rotate(90 + degrees2);
					System.out.println(90 + degrees2);
				}
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				break;
			case zero: //kleine zwarte lijn
////			double distb = sonar.getDistance();
////			System.out.println(distb);
////			if (distb == 255){
//			int i = 0;
//			while(i<360 && !suppressed && !(Calibrate.isblack(SensorPort.S3.readRawValue()))){
//					pilot.rotate(40);
//					i+=10;
//				}
//			while (!suppressed && !(Calibrate.isbrown(SensorPort.S3.readRawValue()))){
//					pilot.rotate(12);
//					pilot.travel(40);
////					
//				}
				
				lastSmallLine = 1;
				pilot.rotate(360,true);
				interruptRotateToBlack();
				while (!suppressed && !(Calibrate.isbrown(SensorPort.S3.readRawValue()))){
					pilot.rotate(12);
					pilot.travel(40);
					
				}
				
				
////			}
////			else{
////			float angle = (float) Math.toDegrees(Math.asin(11/distb));
////			System.out.println(angle);
////			while(!Button.ENTER.isPressed())
////				Thread.yield();
////			pilot.rotate((float) (1.95*angle));
////			pilot.travel(100);
//			}
//	//		pilot.rotate(90, !Calibrate.isblack(SensorPort.S3.readRawValue()));
//			
////			pilot.backward();
////			pilot.rotate(180);
////			pilot.travel(1);
				break;
			case f: //kleine witte lijn
//			double distw = sonar.getDistance();
//			System.out.println(distw);
//			if (distw == 255){
//			int ii = 0;
//				while(!suppressed && !(Calibrate.iswhite(SensorPort.S3.readRawValue()))){
//					pilot.rotate(-40);
//					ii+=10;
//				}
//				while (!suppressed && !(Calibrate.isbrown(SensorPort.S3.readRawValue()))){
//					pilot.rotate(-40);
//					pilot.travel(20);
//				}
				
				lastSmallLine = 2;
				pilot.rotate(-360,true);
				interruptRotateToWhite();
				while (!suppressed && !(Calibrate.isbrown(SensorPort.S3.readRawValue()))){
					pilot.rotate(-12);
					pilot.travel(40);
					
				}
				
//			}
					
//			else{
//			float angle = (float) Math.toDegrees(Math.asin(11/distw));
//			System.out.println(angle);
//			while(!Button.ENTER.isPressed())
//				Thread.yield();
//			pilot.rotate((float) (1.95*angle));
//			pilot.travel(100);
//			}
				break;
			case one:
				break;
			case two:
				Motor.A.setSpeed(700);
				Motor.B.setSpeed(720);
				pilot.travel(700, true);
				interruptTravel();
				break;
			case four:
				break;
			case five:
				break;
			case seven: //wip
				pilot.setSpeed(540);
				pilot.travel(650,true);
				interruptTravel();
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				break;
			case eight:
				break;
			case nine:
				break;
			case a:
				break;
			case b:
				break;
			case c: //bocht naar rechts fout gelezen en gecorrigeerd
				pilot.travel(200,true);
				interruptTravel();
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				pilot.rotate(-90);
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				break;
			case d:
				break;
			case e:
				break;
			default:
				LCD.clear();
				System.out.println("in default");
			while (!suppressed && pilot.isMoving()) {
					Thread.yield();
				}
			}
		} catch (Exception e6) {
			System.out.println("geen volledig gelezen barcode");
		}

		while (!suppressed && pilot.isMoving())
			Thread.yield();
		barcodeReader.reset();
		pilot.stop();

	}

	private void interruptTravel() {
		try{
		while (pilot.isMoving() && !suppressed)
			Thread.yield();
		pilot.stop();
		}
		catch(Exception e3){
			System.out.println("fout in interruptravel");
		}
		
	}

	private void interruptRotateToWhite() {
		try{
		while(pilot.isMoving()){
			if(Calibrate.iswhite(SensorPort.S3.readRawValue())){
				pilot.stop();
			}
		}
		}
		catch(Exception e4){
			System.out.println("fout in interruptRotateToWhite");
		}
	}

	private void interruptRotateToBlack() {
		try{
		while(pilot.isMoving()){
			if(Calibrate.isblack(SensorPort.S3.readRawValue())){
				pilot.stop();
			}
		}
		}
		catch(Exception e5){
			System.out.println("fout in interruptRotateToBlack");
		}
		
	}
	
	
	
	@Override
	public void suppress() {
		barcodeReader.setSuppressed(true);
		suppressed = true;
	}

	@Override
	public boolean takeControl() {
		int value = SensorPort.S3.readRawValue();
		return Calibrate.isblack(value) || Calibrate.iswhite(value);
	}

}
