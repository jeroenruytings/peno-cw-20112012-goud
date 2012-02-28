package pacmansystem.ai.robot.fysicalRobot.connector;

public class VirtuBot {

 
	 private String name;
	 private int ultrasonic;
	 private int lightSensor;
	 private boolean pushSensor;
	 private int infraredSensor;
	
	
	public void turn(int degrees){
		
	}
	
	public void drive(int distance){
		
	}
	
	public void turnHead(int degrees){
		
	}
	
	public int getSensorValue(pacmansystem.ai.robot.Sensor sensor){
		return 0;
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

		public int getTachoCount() {
			// TODO Auto-generated method stub
			return 0;
		}
	
	
	
	

}
