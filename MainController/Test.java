import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class Test {

	//public static final String color[] = {"zwart", "violet", "purple", "blue", "green", "lime", "yellow", "orange", "red", "crimson", "magenta", "pastels", "pastels", "pastels", "pastels", "pastels", "pastels", "wit"};
	static LightSensor sensor = new LightSensor(SensorPort.S4, true);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		calibrate();
		
		while(!Button.ENTER.isPressed()){
			showColor(sensor);
		}

	}
	
	public static void showColor(LightSensor sensor) {
	
		LCD.drawString("Colorshade: " + sensor.readValue(), 0,0);
		LCD.drawString("High: " + sensor.getHigh(),0, 1);
		LCD.drawString("Low: " + sensor.getLow(),0, 2);
	
	}

	public static void calibrate(){
		sensor.calibrateHigh();
		LCD.drawString("Press enter", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		// roteer links
		sensor.calibrateLow();
		LCD.drawString("Succes", 0, 1);
		if(sensor.getHigh() < sensor.getLow()){
			int i = sensor.getHigh();
			sensor.setHigh(sensor.getLow());
			sensor.setLow(i);
		}
		LCD.clear();
	}
}