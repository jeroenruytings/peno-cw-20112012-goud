import lejos.nxt.Button;

public class MainController {
	public static void main(String[] args) {
		// make drive in polygon
		System.out.println("scanning");
		BarcodeThread one = new BarcodeThread();
		one.start();
		Button.waitForPress();
	//	straight.start();
}
}
