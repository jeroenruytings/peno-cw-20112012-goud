	import lejos.nxt.*;
import lejos.robotics.proposal.DifferentialPilot;


public class ultraSonic implements Runnable{
	
	DifferentialPilot p;
	UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
	
	public ultraSonic(DifferentialPilot pilot){
		p = pilot;
	}
	
	public void start()
	{
		LCD.drawString("start", 0, 0);
		Thread mv = new Thread(new ultraSonic(p));
		mv.start();
	}


	  public void run(){
	    UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
	    int counter = 0;

	    try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	      
	    
	    while (!Button.ESCAPE.isPressed()) {
	      sonic.ping();
	      LCD.drawInt(sonic.getDistance(), 0, counter);
	      counter++;
	      
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	      
	    }  
	   

	  }
}
