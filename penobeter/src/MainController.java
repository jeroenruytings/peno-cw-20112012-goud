import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;

public class MainController {
	private static DifferentialPilot p = new DifferentialPilot(55.2f, 54.8f, 113f, Motor.A, Motor.B, false);
	public static MuurvolgerThread two = new MuurvolgerThread(p);
	public static BarcodeThread one = new BarcodeThread();
	public static void main(String[] args) {
		// make drive in polygon
		//System.out.println("scanning");
		
		while (!enterNumber()){
			LCD.drawInt(number, 0, 0);
		};
		
		
		DataPasser dp = new DataPasser();
		Thread t = new Thread(dp);
		
		if(number == 1){
			ImprovedDifferentialPilot idp = ImprovedDifferentialPilot.p;
			idp.setSpeed(360);
			BarcodeThread.calibrate();
			BarcodeThread one = new BarcodeThread();
			one.start();
			idp.forward();
			t.start();
		}
		
		if(number == 2){
			
			p.setSpeed(360);
			
			two.start();
			t.start();
			p.travel(Float.MAX_VALUE);
		}
		
		
		
		
		
		Button.waitForPress();
	//	straight.start();
}
	
	static int number = 2;
	
	private static boolean enterNumber() {
	        if (Button.ENTER.isPressed()){
	        	Button.ENTER.waitForPressAndRelease();
	        	return true;
	        }
	            
	        if (Button.LEFT.isPressed()){
	            number--;
	        	Button.LEFT.waitForPressAndRelease();
	            LCD.clear();
	        }
	        if (Button.RIGHT.isPressed()){
	            number++;
	        	Button.RIGHT.waitForPressAndRelease();
	            LCD.clear();
	        }
	        return false;
	    }
	
	
	private static DataPasser dp;
	
	public static DataPasser getDataPasser(){
		return dp;
	}
	
}
