import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

public class SonarBehavior extends LeoBehavior{
    
    UltrasonicSensor sonic = new UltrasonicSensor(sonarSensor);
    Boolean suppressed = false;
    private int aheadDistance;
    private int rightDistance;
    private int leftDistance;
    int tmpLeft;
    boolean closeLeft = false;
    boolean closeAhead = false;
    boolean farLeft = false;
    
    boolean done = true;
    {
        pilot.setSpeed(720);
    }
     public void action() {
        
        if(closeAhead){
            pilot.stop();
            Motor.C.rotate(100);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            
            rightDistance = sonic.getDistance();
            
            System.out.println("rightDistance : " + rightDistance);
            Motor.C.rotate(-200);
            
    
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            
            leftDistance = sonic.getDistance();
            System.out.println("leftDistance : " + leftDistance);
            
            if(rightDistance < leftDistance)
                pilot.rotate(45);
            else
                pilot.rotate(-45);
            Motor.C.rotate(100);
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
    
            }
            pilot.forward();
            closeAhead = false;
        }
        
        else if(closeLeft){
            steerLeft(leftDistance);
            closeLeft = false;
            Motor.C.rotate(100);
        }
        
        else if(farLeft){
            if(leftDistance > 50){
                Motor.A.setSpeed(500);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                }
                Motor.A.setSpeed(720);
            }
            else{
                Motor.A.setSpeed(600);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                }
                Motor.A.setSpeed(720);
            }
            farLeft = false;
            Motor.C.rotate(100);
        }
//        
//        done = true;
    }
    public void suppress() {
        suppressed = true;
        
    }
    public boolean takeControl() {
//    if (done){
//        done = false;
        aheadDistance = sonic.getDistance();
        if(aheadDistance < 25){
            closeAhead = true;
            return true;
        }
        
//        Motor.C.rotate(-100);
//        
//        tmpLeft = sonic.getDistance();
//        
//        if(tmpLeft < 30){
//            leftDistance = tmpLeft;
//            closeLeft = true;
//            return true;
//        }
//        
//        if((tmpLeft > 35) && (tmpLeft < 90)){
//            leftDistance = tmpLeft;
//            farLeft = true;
//            return true;
//        }
//        
//        Motor.C.rotate(100);
////        done = true;
////        }
        return false;
    }
    
    public void steerLeft(int distance){
        System.out.println("Leftdistance: " + distance);
        Sound.twoBeeps();
        if (distance < 15) {
            Motor.B.setSpeed(520);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
            }
            Motor.B.setSpeed(720);
        } else {
            Motor.B.setSpeed(620);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
            }
            Motor.B.setSpeed(720);
        }
    }
}
