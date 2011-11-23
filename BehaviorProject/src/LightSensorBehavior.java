import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class LightSensorBehavior extends LeoBehavior {

	boolean suppressed = true;
	UltrasonicSensor sonar = new UltrasonicSensor(sonarSensor);


	@Override
	public void action() {
		suppressed = false;
		pilot.setSpeed(360);
		pilot.forward();
		try{
		barcodeReader.run();
		}
		catch (Exception e){
			System.out.println("fout in barcode");
			pilot.stop();
			Button.ENTER.waitForPressAndRelease();
			barcodeReader = new BarcodeReader();
			System.out.println("nieuwe reader aangemaakt");
			Button.ENTER.waitForPressAndRelease();
			barcodeReader.setLastRead(BarcodeReader.barcode.a);
		}
		pilot.stop();
		while (!suppressed && pilot.isMoving()) {
			Thread.yield();
		}
		pilot.setSpeed(540);
	//	BarcodeBehavior.setTakeControleTrue();
		
		// links is positief roteren
		switch (barcodeReader.lastRead()) {
		case six:
			pilot.travel(100);
			while(!suppressed && pilot.isMoving())
				Thread.yield();
			pilot.travel(100);
			while(!suppressed && pilot.isMoving())
				Thread.yield();
			pilot.rotate(-90);
			while(!suppressed && pilot.isMoving())
				Thread.yield();
			break;
		case three:
			pilot.travel(100);
			while(!suppressed && pilot.isMoving())
				Thread.yield();
			pilot.travel(100);
			while(!suppressed && pilot.isMoving())
				Thread.yield();
			pilot.rotate(90);
			while(!suppressed && pilot.isMoving())
				Thread.yield();
			break;
		case zero:
////			double distb = sonar.getDistance();
////			System.out.println(distb);
////			if (distb == 255){
			int i = 0;
			while(i<360 && !suppressed && !(Calibrate.isblack(SensorPort.S3.readRawValue()))){
					pilot.rotate(40);
					i+=10;
				}
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
		case f:
//			double distw = sonar.getDistance();
//			System.out.println(distw);
//			if (distw == 255){
			int ii = 0;
				while(!suppressed && !(Calibrate.iswhite(SensorPort.S3.readRawValue()))){
					pilot.rotate(-10);
					ii+=10;
				}
				while (!suppressed && !(Calibrate.isbrown(SensorPort.S3.readRawValue()))){
					pilot.rotate(-40);
					pilot.travel(20);
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
			break;
		case four:
			break;
		case five:
			break;
		case seven:
			int j = 0;
			while (!suppressed && j < 5) {
				pilot.travel(100);
				j++;
			}
			break;
		case eight:
			break;
		case nine:
			break;
		case a:
			break;
		case b:
			break;
		case c:
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

		while (!suppressed && pilot.isMoving())
			Thread.yield();
		barcodeReader.reset();
		pilot.stop();


	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public boolean takeControl() {
		int value = SensorPort.S3.readRawValue();
		return Calibrate.isblack(value) || Calibrate.iswhite(value);
	}

}
