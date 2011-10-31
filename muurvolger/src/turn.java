import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;


public class turn {
	
	DifferentialPilot pilot;
	
    private void turn360(){
    	Motor.A.setSpeed(360);
    	Motor.B.setSpeed(360);
    	while(pilot.getLeftCount() < 7200 ){
    		Motor.A.forward();
    		Motor.B.backward();
    	}
        LCD.drawString("Left " + pilot.getLeftCount(), 0,0);
        LCD.drawString("Right " + pilot.getRightCount(),0,1);
        LCD.drawString("Maxsnelheid " + pilot.getMoveMaxSpeed(), 0, 2);
    }
    
	public static void main(String[] args) {
		turn tn = new turn();
		tn.pilot = new DifferentialPilot(55.5f, 55.1f,113f, Motor.A, Motor.B, false);
	    LCD.clear();
	    tn.turn360();
	   
	}

}
