package MainController;



import lejos.nxt.LCD;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Stopwatch;


public class LineFollower {

	int treshold;
	int min;
	int max;
	boolean darkline;
	
	public static LightSensor sensor = new LightSensor(SensorPort.S4, true);
	
	ImprovedDifferentialPilot pilot = new ImprovedDifferentialPilot(56f, 55.5f, 113f, Motor.A, Motor.B, false);

	
	public LineFollower(){
		darkline = true;
		treshold = 0;
		min = (int) Double.POSITIVE_INFINITY;
		max = (int) Double.NEGATIVE_INFINITY;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LineFollower lf = new LineFollower();

		
		lf.calibrate();
		lf.findLineEdge();
		
		while(true){
			lf.followLine();
		}
		

	}
	
	public void calibrate() {
		Stopwatch watch = new Stopwatch();
		watch.reset();
		while (watch.elapsed() <= 1000);
		int color = sensor.getNormalizedLightValue();
		min = color;
		max = color;
		
		LCD.drawString("Value" + sensor.getNormalizedLightValue(),0,0);
		LCD.drawString("High: " + getMax(), 0, 1);
		LCD.drawString("Low: " + getMin(), 0, 2);
		LCD.drawString("Dark: " + isDark(), 0, 4);
		pilot.rotate(60);{
	
			color = sensor.getNormalizedLightValue();
			if (color > max){
				setDark(true);
				max = color;
			}
			else if (color < min){
				setDark(false);
				min = color;
			}
			LCD.drawString("Value" + sensor.getNormalizedLightValue(),0,0);
			LCD.drawString("High: " + getMax(), 0, 1);
			LCD.drawString("Low: " + getMin(), 0, 2);
			LCD.drawString("Dark: " + isDark(), 0, 4);
		}
		treshold = (min+max)/2;
	}
	
  	private void setDark(boolean b) {
		darkline = b;
		
	}
  	
  	private int getTreshold(){
  		return treshold;
  	}

	public void findLineEdge(){
		
  		if (darkline){
  			while((sensor.getNormalizedLightValue() - treshold) > 0){
  				LCD.drawString("Verschilt: " + (sensor.getNormalizedLightValue() - treshold), 0,3);
  				LCD.drawString("Value" + sensor.getNormalizedLightValue(),0,0);
  				LCD.drawString("High: " + getMax(), 0, 1);
  				LCD.drawString("Low: " + getMin(), 0, 2);
  				LCD.drawString("Dark: " + isDark(), 0, 4);
  				pilot.rotate(-3);
  			}
  		}
		else{
			while((sensor.getNormalizedLightValue() - treshold) < 0){
				LCD.drawString("Verschilt: " + (sensor.getNormalizedLightValue() - treshold), 0,3);
				LCD.drawString("Value" + sensor.getNormalizedLightValue(),0,0);
				LCD.drawString("High: " + getMax(), 0, 1);
				LCD.drawString("Low: " + getMin(), 0, 2);
				LCD.drawString("Dark: " + isDark(), 0, 4);
				pilot.rotate(-3);
			}
		}
	}
	
	private boolean isDark() {
		return darkline;
	}

	
	
	public  void recovery(){
		Motor.A.reverseDirection();
		Motor.B.reverseDirection();
		pilot.forward();
		while (!inRange());
		pilot.stop();
		Motor.A.reverseDirection();
		Motor.B.reverseDirection();
	}
	
	public void isLost(){
		pilot.stop();
		recovery();
		
	}
	
	public boolean inRange(){
		int color = sensor.getNormalizedLightValue();
		// 5 nog aanpassen naar procenten
		return (color < (treshold+7) && color > (treshold-7));
	}

	public  int getMax(){
		return max;
	}
	
	public int getMin(){
		return min;
	}
	
	public void followLine(){
		pilot.setStandardSpeed(100);
		pilot.forward();
		while (inRange());
		pilot.stop();
		
		 if(sensor.getNormalizedLightValue()>getTreshold()){
			if(isDark()){
				pilot.stopWheel(Motor.B);
				pilot.forward();
				while(!inRange()){
					pilot.stopWheel(Motor.B);
					rotateIfFarFromLine();
				}
				pilot.stop();
			}
			else{
				pilot.stopWheel(Motor.A);
				pilot.forward();
				while(!inRange()){
					pilot.stopWheel(Motor.A);
					rotateIfOnLine();
				}
				pilot.stop();
			}
		}
		else{
			if(isDark()){
				pilot.stopWheel(Motor.A);
				pilot.forward();
				while(!inRange()){
					pilot.stopWheel(Motor.A);
					rotateIfOnLine();
				}
				pilot.stop();
			}
			else{
				pilot.stopWheel(Motor.B);
				pilot.forward();
				while(!inRange()){
					pilot.stopWheel(Motor.B);
					rotateIfFarFromLine();
				}
				pilot.stop();
			}
		}
		
	}
	
	private void rotateIfFarFromLine() {
		int i = 0;
		if (isFarFromLine()){
			pilot.stop();
			while (!inRange()){
				pilot.rotate(-3);
				if (++i>=360){
					isLost();
					break;
				}
			}
		}
	}

	private boolean isFarFromLine() {
		if(isDark())
			return (sensor.getNormalizedLightValue()>(getMax()-5));
		else 
			return (sensor.getNormalizedLightValue()<(getMin()+5));
	}

	private void rotateIfOnLine(){
		int i = 0;
		if (isOnLine()){
			pilot.stop();
			while (!inRange()){
				pilot.rotate(1);
				if(++i>=360){
					isLost();
					break;
				}
			}
		}
	}
	
	private boolean isOnLine() {
		if(isDark())
			return (sensor.getNormalizedLightValue() < (getTreshold()-15));
		else 
			return (sensor.getNormalizedLightValue() > (getTreshold()+15));
	}

	
}