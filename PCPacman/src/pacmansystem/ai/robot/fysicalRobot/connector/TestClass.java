package pacmansystem.ai.robot.fysicalRobot.connector;

public class TestClass {
	
	static MoverLayer mover;
	
	public TestClass(){
		
	}
	
	public static void main(String [] args){
		
		MoverLayer mover = new MoverLayer();
		System.out.println("Pcc: " + mover.getPcc());
		mover.initialiseMoverLayer();
		System.out.println("Virtu:" + mover.getPcc().getVirtu());
		mover.getBarcodeReader().calibrate(mover);
		mover.drive(5);
		
		
	}

}
