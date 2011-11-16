import java.util.Queue;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;

public class MainController {
	private static DifferentialPilot p = new DifferentialPilot(54.25f, 54.75f,
			161.6f, Motor.A, Motor.B, false);
	public static MuurvolgerThread two = new MuurvolgerThread(p);
	public static BarcodeThread one = new BarcodeThread();
	public static LineFollowerThread three = new LineFollowerThread();

	private static Segment[] barcodes = new Segment[16];

	public static void main(String[] args) {
		// make drive in polygon
		// System.out.println("scanning");

		while (!enterNumber()) {
			LCD.drawInt(number, 0, 0);
		}
		;

		DataPasser dp = new DataPasser();
		Thread t = new Thread(dp);

		if (number == 1) {
			ImprovedDifferentialPilot idp = ImprovedDifferentialPilot.p;
			idp.setSpeed(360);
			BarcodeThread.calibrate();
			BarcodeThread one = new BarcodeThread();
			one.start();
			// idp.forward();
			t.start();

			BarcodeThread.barcode lastb = null;
			// BarcodeThread one = new BarcodeThread();
			one.start();
			boolean robothasaclue = false;
			Queue segments = new Queue();
			idp.forward();
			System.out.println("starting while");
			initializeBarcodes();
			while (true) {
				if (robothasaclue) {
					Segment segment = (Segment) segments.pop();
					if (segment != null) {
						if (one.hasNewBarcode())
							System.out
									.println("dees mag nooooit afgeprint worden&");
						segment.driveWith(idp);
						idp.forward();
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							
						}
						robothasaclue = false;

					} else {
						robothasaclue = false;
					}
				} else {
					if (one.getBarcode() != 666 && one.hasNewBarcode())// new
																		// barcode
																		// !
					{
						// System.out.println("barcode detected"+current);
						segments.push(barcodes[one.getBarcode()]);
						robothasaclue = true;
						one.setNewBarcode(false);
					}

				}
			}

		}

		if (number == 2) {

			p.setSpeed(360);

			two.start();
			t.start();
			p.travel(Float.MAX_VALUE);
		}

		if (number == 3) {
			three.start();
			t.start();
		}

		Button.waitForPress();
		// straight.start();
	}

	static int number = 2;

	private static void initializeBarcodes() {
		// BarcodeThread.barcode[] codes = BarcodeThread.barcode.values();
		for (int i = 0; i < barcodes.length; i++) {
			barcodes[i] = Segment.DEFAULT;
		}
		barcodes[BarcodeThread.barcode.six.ordinal()] = new Segment() {
			@Override
			public void driveWith(ImprovedDifferentialPilot t) {
				t.travel(200);
				t.rotate(90);
			}
		};
		barcodes[BarcodeThread.barcode.three.ordinal()] = new Segment() {

			@Override
			public void driveWith(ImprovedDifferentialPilot t) {
				t.travel(200);
				t.rotate(-90);
			}
		};

	}

	private static boolean enterNumber() {
		if (Button.ENTER.isPressed()) {
			Button.ENTER.waitForPressAndRelease();
			return true;
		}

		if (Button.LEFT.isPressed()) {
			number--;
			Button.LEFT.waitForPressAndRelease();
			LCD.clear();
		}
		if (Button.RIGHT.isPressed()) {
			number++;
			Button.RIGHT.waitForPressAndRelease();
			LCD.clear();
		}
		return false;
	}

	private static DataPasser dp;

	public static DataPasser getDataPasser() {
		return dp;
	}

}
