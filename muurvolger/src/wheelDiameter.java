import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;



public class wheelDiameter {
	
	TachoPilot pilot;

    public static long fromMmToInch(long Mm){
    	long inch;
    	inch = (long) (Mm / 25.4);
    	return inch;
    }
    
    private void go(){
    	Motor.A.setSpeed(360);
    	Motor.B.setSpeed(360);
    	
    	while(pilot.getLeftCount() < 1080 ){
    		Motor.A.forward();
    		Motor.B.forward();
    	}
        LCD.drawString("Left " + pilot.getLeftCount(), 0,0);
        LCD.drawString("Right " + pilot.getRightCount(),0,1);
        
        while(Button.ENTER.isPressed()){
        	Motor.A.stop();
        	Motor.B.stop();
        }
        	
        
    	Motor.A.setSpeed(360);
    	Motor.B.setSpeed(360);
    	
    	while(pilot.getLeftCount() < 2160 ){
    		Motor.A.forward();
    		Motor.B.forward();
    	}
    }
    
	public static void main(String[] args) {
		wheelDiameter wd = new wheelDiameter();
		wd.pilot = new TachoPilot(fromMmToInch(56), fromMmToInch(115), Motor.A, Motor.B);
	    LCD.clear();
	    int teller = 0;
	    while(teller > 100)
	    	teller++;
	    wd.go();
	   
	}
}
