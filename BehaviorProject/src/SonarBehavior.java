import lejos.nxt.Motor;
import lejos.nxt.Sound;

public class SonarBehavior extends LeoBehavior{
    Boolean suppressed = false;
    boolean closeLeft = false;
    boolean closeAhead = false;
    boolean closeRight = false;
    boolean first = true;
    int leftDistance;
    int rightDistance;
    
    public void action() {
        suppressed = false;
        Sound.twoBeeps();
        pilot.stop();
        Motor.C.rotate(-90);
        leftDistance = MuurUpdater.sonic.getDistance();
        Motor.C.rotate(180);
        rightDistance = MuurUpdater.sonic.getDistance();
            if(rightDistance < leftDistance){
    			pilot.travelArc(-200, 250);
            }
            else{
    			pilot.travelArc(200, -250);
            }  
        suppressed = true;
        }
    public void suppress() {
        suppressed = true;
        
    }
    public boolean takeControl() {
		Sound.twoBeeps();
    	if(MuurUpdater.aheadDistance < 10){
    		return true;  
    	}
        return false;
    }
    
    public void steerLeft(int distance){
        System.out.println("Leftdistance: " + distance);
        if (distance < 15) {
            Motor.B.setSpeed(360);
        } else {
            Motor.B.setSpeed(480);
        }
        
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
        }
        Motor.B.setSpeed(720);
    }
    
    public void steerRight(int distance){
        System.out.println("Leftdistance: " + distance);
        if (distance < 25) {
            Motor.A.setSpeed(360);
        } else {
            Motor.A.setSpeed(480);
        }
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
        }
        Motor.A.setSpeed(720);
    }
}
