

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;


public class straightLine {

	DifferentialPilot pilot;
	static int number = 1;
	static int snelheid = 360;
	static int length = 20000;

	
	public void moveForward(){
	    while(!enterSnelheid()){;
	        LCD.drawString("Snelheid a: ", 0,0);
	        LCD.drawInt(snelheid,0,1);
	    }

	    
	    while(!enterLength()){
	        LCD.drawString("Afstand (in mm) ", 0,0);
	        LCD.drawInt(length,0,1);
	    }
	    
		pilot.setSpeed(snelheid);
		
        pilot.travel(length);
        
		LCD.clear();

		Button.waitForPress();
	}
	
	private static boolean enterLength(){
        if (Button.ENTER.isPressed()){
        	Button.ENTER.waitForPressAndRelease();
            return true;
        }
        if (Button.LEFT.isPressed()){
            length -= 100;
        	Button.LEFT.waitForPressAndRelease();
            LCD.clear();
        }
        if (Button.RIGHT.isPressed()){
        	Button.RIGHT.waitForPressAndRelease();
            length += 100;
            LCD.clear();
        }
        return false;
    }
	
    private static boolean enterSnelheid() {
        if (Button.ENTER.isPressed()){
        	Button.ENTER.waitForPressAndRelease();
            return true;
        }
        if (Button.LEFT.isPressed()){
            snelheid -= 20;
        	Button.LEFT.waitForPressAndRelease();
            LCD.clear();
        }
        if (Button.RIGHT.isPressed()){
        	Button.RIGHT.waitForPressAndRelease();
            snelheid += 20;
            LCD.clear();
        }
        return false;
    }
    
	//linkerwiel moet groter zijn dan rechterwiel
    //


	public static void main(String[] args) {

		straightLine sl = new straightLine();
		sl.pilot = new DifferentialPilot(55.2f, 54.8f,113f, Motor.A, Motor.B, false);
		muurVolger p = new muurVolger(sl.pilot);
		Thread t = new Thread(p);
		t.start();
		sl.moveForward();
		
	      
	    
	}

}
