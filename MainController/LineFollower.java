package MainController;





//import lejos.nxt.Button;


import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;


public class LineFollower {

	int treshold;
	int min;
	int max;
	boolean darkline;
	
	public static LightSensor sensor = new LightSensor(SensorPort.S4, true);
	
	ImprovedDifferentialPilot pilot = new ImprovedDifferentialPilot(56f, 55.5f, 113f, Motor.A, Motor.B, false);
	
	public LineFollower(){
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
		lf.showValues();
		lf.findLineEdge();
		
		while(true)
			lf.followLine();
		

	}
	
	public void calibrate() {
		int color = sensor.getNormalizedLightValue();
		min = color;
		max = color;
		
		pilot.rotate(45);
		for(int i = 1; i<=45; i++){
			pilot.rotate(1);
			color = sensor.getNormalizedLightValue();
			if (color > max){
				setDark(true);
				max = color;
			}
			else if (color < min){
				setDark(false);
				min = color;
			}
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
  				pilot.rotate(-5);
  				}
  			}
		else{
			while((sensor.getNormalizedLightValue() - treshold) < 0){
				LCD.drawString("Verschilt: " + (sensor.getNormalizedLightValue() - treshold), 0,3);
				LCD.drawString("Value" + sensor.getNormalizedLightValue(),0,0);
				LCD.drawString("High: " + getMax(), 0, 1);
				LCD.drawString("Low: " + getMin(), 0, 2);
				LCD.drawString("Dark: " + isDark(), 0, 4);
				pilot.rotate(-5);
		}
		}
	}
	
	private boolean isDark() {
		return darkline;
	}

	public void followLine(){
		pilot.setStandardSpeed(720);
		pilot.forward();
		while (inRange());
		pilot.stop();
		if(sensor.getNormalizedLightValue()>getTreshold()){
			if(isDark()){
				pilot.stopWheel(Motor.B);
				pilot.forward();
				while(!inRange())
					pilot.stopWheel(Motor.B);
				pilot.stop();
			}
			else{
				pilot.stopWheel(Motor.A);
				pilot.forward();
				while(!inRange())
					pilot.stopWheel(Motor.A);
				pilot.stop();
			}
		}
		else{
			if(isDark()){
				pilot.stopWheel(Motor.A);
				pilot.forward();
				while(!inRange()){
					pilot.stopWheel(Motor.A);
				}
				pilot.stop();
			}
			else{
				pilot.stopWheel(Motor.B);
				pilot.forward();
				while(!inRange()){
					pilot.stopWheel(Motor.B);
				}
				pilot.stop();
			}
		}
		
	}
	
	public void recovery(){
		for(int i = 0; i <= 360 && !inRange(); i+=5){
			pilot.rotate(5);
		}
		//rijd in spiraal
	}
	
	public boolean inRange(){
		int color = sensor.getNormalizedLightValue();
		// 5 nog aanpassen naar procenten
		return color < (treshold+5) && color > (treshold-5);
	}

	public  int getMax(){
		return max;
	}
	
	public int getMin(){
		return min;
	}
	
	public void showValues(){
		for (int i = 0; i<=20; i++){
			LCD.drawString("Value" + sensor.getNormalizedLightValue(),0,0);
			LCD.drawString("High: " + getMax(), 0, 1);
			LCD.drawString("Low: " + getMin(), 0, 2);
			}
		}
	

}}}