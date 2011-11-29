import lejos.nxt.Motor;
import lejos.nxt.Sound;

public class SonarBehavior extends LeoBehavior{
    Boolean suppressed = false;
    boolean closeLeft = false;
    boolean closeAhead = false;
    boolean closeRight = false;
    
    public void action() {
        suppressed = false;
        Sound.twoBeeps();
        if(closeAhead && !suppressed){
            if(MuurUpdater.rightDistance < MuurUpdater.leftDistance){
                pilot.rotate(90);
            }
            else{
                pilot.rotate(-90);
            }
            closeAhead = false;
        }
        
        else if(closeRight && !suppressed){
            steerRight(MuurUpdater.rightDistance);
            closeRight = false;
            
        }
        
        else if(closeLeft && !suppressed){
            steerLeft(MuurUpdater.leftDistance);
            closeLeft = false;
        }
        
        suppressed = true;
    }
    public void suppress() {
        suppressed = true;
        
    }
    public boolean takeControl() {
        if(MuurUpdater.aheadDistance < 30 && MuurUpdater.newValuesAhead){
            closeAhead = true;
            MuurUpdater.newValuesAhead = false;
            return true;
        }
        
        if(MuurUpdater.leftDistance < 20 && MuurUpdater.newValuesLeft){
            closeLeft = true;
            MuurUpdater.newValuesLeft = false;
            return true;
        }
        
        if(MuurUpdater.rightDistance < 30 && MuurUpdater.newValuesRight){
            closeRight = true;
            MuurUpdater.newValuesRight = false;
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
