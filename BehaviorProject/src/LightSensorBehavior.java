import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class LightSensorBehavior extends LeoBehavior {

	boolean suppressed = true;
	int lastSmallLine = 0;

	@Override
	public void action() {
		Communicator.instance().send(
				new Message(Monitor.BehaviorMonitor, 
						BehaviourIdentifier.LightSensorBehaviour, 
						BehaviourReason.LIGHTSENSORNOTBROWN));
		suppressed = false;
		barcodeReader.setSuppressed(false);
		pilot.setSpeed(360); 
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
			Message msg = new Message(Monitor.SensorMonitor, SensorIdentifier.BarcodeSensor, new SensorValue((byte)barcodeReader.lastRead().ordinal()));
			Communicator.instance().send(msg);
			switch (barcodeReader.lastRead()) {
			case six: //bocht rechts
				double dist = barcodeReader.getTotaldist();
				float degrees = (float) (dist-290)*(45)/(446-290);
				//System.out.println(degrees);
				if(lastSmallLine==1){//zwarte lijn
					pilot.rotate(-degrees);
					//System.out.println(-degrees);
					}
				else if(lastSmallLine==2){//witte lijn
					pilot.rotate(degrees);
					//System.out.println(degrees);
				}
				pilot.travel(250,true);
				interruptTravel();
				pilot.rotate(-90);
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				setVersmalling(false);
				break;
			case three: //bocht links
				double dist2 = barcodeReader.getTotaldist();
				float degrees2 = (float) (dist2-290)*(45)/(446-290);
				//System.out.println(degrees2);
				if(lastSmallLine==1){//zwarte lijn
					pilot.rotate(-degrees2);
					//System.out.println(-degrees2);
					}
				else if(lastSmallLine==2){//witte lijn
					pilot.rotate(degrees2);
					//System.out.println(degrees2);
				}
				pilot.travel(250,true);
				interruptTravel();
				pilot.rotate(90);
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				setVersmalling(false);
				break;			
//				
//				//pilot.rotate(-80);
//				while(!suppressed && pilot.isMoving())
//					Thread.yield();
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
				while (!suppressed && (Calibrate.isblack(SensorPort.S3.readRawValue()))){
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
				while (!suppressed && (Calibrate.iswhite(SensorPort.S3.readRawValue()))){
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
			case one: //rechtdoor rijden: niks doen
				setVersmalling(false);
				break;
			case two: //omhoog
//				Motor.B.setSpeed(450);
//				Motor.A.setSpeed(520);
//				pilot.travel(700, true);
//				interruptTravel();
				setVersmalling(false);
				break;
			case four: //omlaag
				setVersmalling(false);
				break;
			case five:
				setVersmalling(false);
				break;
			case seven: //wip
				Motor.B.setSpeed(450);
				Motor.A.setSpeed(520);
				pilot.travel(650,true);
				interruptTravel();
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				setVersmalling(false);
				break;
			case eight: //versmalling: niks doen
				setVersmalling(true);
				break;
			case nine:
				setVersmalling(false);
				break;
			case a:
				setVersmalling(false);
				break;
			case b:
				setVersmalling(false);
				break;
			case c: //bocht naar rechts fout gelezen en gecorrigeerd
				double dist3 = barcodeReader.getTotaldist();
				float degrees3 = (float) (dist3-290)*(45)/(446-290);
				//System.out.println(degrees3);
				if(lastSmallLine==1){//zwarte lijn
					pilot.rotate(-degrees3);
					//System.out.println(-degrees3);
					}
				else if(lastSmallLine==2){//witte lijn
					pilot.rotate(degrees3);
					//System.out.println(degrees3);
				}
				pilot.travel(250,true);
				interruptTravel();
				pilot.rotate(-90);
				while(!suppressed && pilot.isMoving())
					Thread.yield();
				setVersmalling(false);
				break;
			case d:
				setVersmalling(false);
				break;
			case e:
				setVersmalling(false);
				break;
			case eightinverse: //versmalling omgekeerd gelezen
				setVersmalling(true);
				pilot.rotate(180);
				pilot.travel(200, true);
				interruptTravel();
			break;
			case oneinverse: //rechtdoor omgekeerd gelezen
				pilot.rotate(180);
				pilot.travel(200,true);
				interruptTravel();
				setVersmalling(false);
			break;
			case fourinverse: //rechtdoor omgekeerd gelezen
				pilot.rotate(180);
				pilot.travel(200,true);
				interruptTravel();
				setVersmalling(false);
			break;
			case twoinverse: //omhoog omgekeerd gelezen
				pilot.travel(200);
				interruptTravel();
				pilot.rotate(180);
				setVersmalling(false);
			break;
			case seveninverse: //wip omgekeerd gelezen
				pilot.travel(200);
				interruptTravel();
				pilot.rotate(180);
				setVersmalling(false);
			break;
			case threeinverse:
				pilot.travel(200);
				interruptTravel();
				pilot.rotate(180);
				setVersmalling(false);
			break;
//			case sixinverse:
//				pilot.travel(200);
//				interruptTravel();
//				pilot.rotate(180);
//				setVersmalling(false);
//			break;
			case leftinverse:
				pilot.travel(200);
				interruptTravel();
				pilot.rotate(180);
				setVersmalling(false);
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
