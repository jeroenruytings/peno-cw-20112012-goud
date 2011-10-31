import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;



public class veelhoek
{
    static int number = 1;
    static int length = 1000;
    
    
    public static long fromMmToInch(float length){
    	long inch;
    	inch = (long) (length / 25.4);
    	return inch;
    }
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
    
    private static boolean enterLength() {
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
            length += 100;
        	Button.RIGHT.waitForPressAndRelease();
            LCD.clear();
        }
        return false;
    }
    
    
    
    public static void main(String[] args)
    {
        DifferentialPilot P = new DifferentialPilot(56f, 55.5f, 116f, Motor.A, Motor.B,false);
        
	    while(!enterNumber()){
	    	
	        LCD.drawString("Aantal hoeken: ", 0,0);
	        LCD.drawInt(number,0,1);
	    }
	    LCD.clear();
	    
	    while(!enterLength()){
	        LCD.drawString("Lengte: ", 0,0);
	        LCD.drawInt(length,0,1);
	    }
	    
	    LCD.clear();
    	Motor.A.setSpeed(360);
    	Motor.B.setSpeed(360);
        for(int i = 0; i<number ; i++)
        {
        	P.travel(length);
            P.rotate(360/number);  
            
        }
    }

}
