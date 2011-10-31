
import lejos.nxt.LCD;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.proposal.DifferentialPilot;


public class muurVolger implements Runnable  {

	DifferentialPilot p;
	UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
	
	public muurVolger(DifferentialPilot pilot){
		p = pilot;
	}
	public void start()
	{
		LCD.drawString("start", 0, 0);
		Thread mv = new Thread(new muurVolger(p));
		mv.start();
	}
	
	
	public void run() {
		int previousDistance = 0;
		int counter = 0;
		while(true){
			int speedA = Motor.A.getSpeed();
			boolean forwardA = true;
			int speedB = Motor.B.getSpeed();
			boolean forwardB = true;
			LCD.clear();
			LCD.drawInt(sonic.getDistance(), 0, 0);
			Sound.twoBeeps();
			sonic.ping();
			
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//			}
			

			int distance = sonic.getDistance();
			double steering = steer(distance, previousDistance);
		
//			if(distance <= 5){
//				Motor.A.reverseDirection();
//				Motor.A.setSpeed(720);
//				Motor.B.reverseDirection();
//				Motor.B.setSpeed(0);
//			}
			//else{
				int steeringPowerB =  (int)(Motor.B.getSpeed() * (1 - steering));
				if (steeringPowerB < 0){
					Motor.B.reverseDirection();
					forwardA = false;
				}
				int steeringPowerA =  (int)(Motor.A.getSpeed() * (1 + steering));
				if (steeringPowerA < 0){
					Motor.A.reverseDirection();
					forwardB = false;
				}
				Motor.B.setSpeed(steeringPowerB);
				Motor.A.setSpeed(steeringPowerA);
			//}
			
		      LCD.drawInt(sonic.getDistance(), 0, counter);
		      counter++;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
//			if (distance <= 5)
//			{
//				Motor.A.reverseDirection();
//				Motor.B.reverseDirection();
//			}
			
			if (!forwardA)
				Motor.A.reverseDirection();
			if (!forwardB)
				Motor.B.reverseDirection();
			Motor.A.setSpeed(speedA);
			Motor.B.setSpeed(speedB);
			previousDistance = distance;

		}
	}
	
	
	public double steer(int distance, int previousDistance){
//		if (distance == 255)
//			distance = previousDistance;
		if ((steer(previousDistance) == 0.6) || (steer(previousDistance) == -0.6)){
			return steer(previousDistance) / 1;
		}
		
		return steer(distance);
	}
	
	public double steer(int distance){
		int realDistance = distance;
		if(distance <= 22)
			realDistance = distance - 3;
		if(45 < realDistance && 60 > realDistance ){ //33		//54
			return 0;
		}
		double a = 4*0.0001; //4.20168*0.0001
		double b = -0.042;//-0.036975
		double c = 1.28;//0.97143
		double uitkomst = a*(realDistance^2) + b*realDistance + c;
		
		if(realDistance < 28){
			uitkomst = 2;
		}
		
		if( uitkomst < -0.4)
			return -0.4;
//		if (uitkomst > 1)
//			return 1;
		if (54 < realDistance){
			return -uitkomst;
		}
		return uitkomst;
	
		
		
	}

	/**
	 * @param args
	 */

}
