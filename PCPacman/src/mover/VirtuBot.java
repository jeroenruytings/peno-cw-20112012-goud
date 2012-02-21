package mover;

import java.util.Vector;

import util.Sensor;

public class VirtuBot {


	 private Coordinate position; 
	 private String name;
	 private int ultrasonic;
	 private int lightSensor;
	 private boolean pushSensor;
	 private int infraredSensor;
	
	
	public void turn(int degrees){
		
	}
	
	public void drive(int distance){
		
	}
	
	public int getSensorValue(Sensor sensor){
		return 0;
	}
	
	 public Coordinate getPosition() {
			return position;
		}

		private void setPosition(Coordinate position) {
			this.position = position;
		}

		public String getName() {
			return name;
		}

		private void setName(String name) {
			this.name = name;
		}

		public int getUltrasonic() {
			return ultrasonic;
		}

		private void setUltrasonic(int ultrasonic) {
			this.ultrasonic = ultrasonic;
		}

		public int getLightSensor() {
			return lightSensor;
		}

		private void setLightSensor(int lightSensor) {
			this.lightSensor = lightSensor;
		}

		public boolean isPushSensor() {
			return pushSensor;
		}

		private void setPushSensor(boolean pushSensor) {
			this.pushSensor = pushSensor;
		}

		public int getInfraredSensor() {
			return infraredSensor;
		}

		private void setInfraredSensor(int infraredSensor) {
			this.infraredSensor = infraredSensor;
		}
	
	
	
	

}
