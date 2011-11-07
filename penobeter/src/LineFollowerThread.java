import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.util.Stopwatch;


public class LineFollowerThread implements Runnable{

        int treshold;
        int min;
        int max;
        boolean darkline;
        
        public static LightSensor sensor = new LightSensor(SensorPort.S3, true);
        
        ImprovedDifferentialPilot pilot = new ImprovedDifferentialPilot(56f, 55.5f, 113f, Motor.A, Motor.B, false);

        
        public LineFollowerThread(){
                darkline = true;
                treshold = 0;
                min = (int) Double.POSITIVE_INFINITY;
                max = (int) Double.NEGATIVE_INFINITY;
        }
        
        
        public void run() {
                LineFollowerThread lf = new LineFollowerThread();

               
                
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
                LCD.drawString("Eerste: " + color, 0, 0);
                min = color;
                max = color;
                
                pilot.rotate(60);{
        
                        color = sensor.getNormalizedLightValue();
                        LCD.drawString("Tweede: " + color, 0, 1);
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
                LCD.drawString("Treshold: " + treshold, 0, 2);
                while(!Button.ENTER.isPressed());
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
                                pilot.rotate(-3);
                        }
                }
                else{
                        while((sensor.getNormalizedLightValue() - treshold) < 0){
                                pilot.rotate(-3);
                        }
                }
        }
        
        private boolean isDark() {
                return darkline;
        }

        
        
        public  void recovery(){
        		pilot.backward();
                while (!inRange());
                pilot.stop();
        }
        
        public void isLost(){
                pilot.stop();
                recovery();
                
        }
        
        public boolean inRange(){
                int color = sensor.getNormalizedLightValue();
                // 5 nog aanpassen naar procenten
                return (color < (treshold+getMarge()) && color > (treshold-getMarge()));
        }

        private int getMarge() {
			return ((getMax()-getMin())/10);
		}

		public  int getMax(){
                return max;
        }
        
        public int getMin(){
                return min;
        }
        
        public void followLine(){
                pilot.setStandardSpeed(360);
                pilot.forward();
                while (inRange()) {
                	LCD.drawString("Waarde: " + sensor.getNormalizedLightValue(), 0, 0);
                	LCD.drawString("FarFrom: " + isFarFromLine(), 0, 1);
                	LCD.drawString("OnLine: " + isOnLine(), 0, 2);

                }
                pilot.stop();
                
                 if(sensor.getNormalizedLightValue()>getTreshold()){
                        if(isDark()){
                                pilot.stopWheel(Motor.B);
                                pilot.forward();
                                while(!inRange()){
                                	LCD.drawString("Waarde: " + sensor.getNormalizedLightValue(), 0, 0);
                                	LCD.drawString("FarFrom: " + isFarFromLine(), 0, 1);
                                	LCD.drawString("OnLine: " + isOnLine(), 0, 2);
                                        pilot.stopWheel(Motor.B);
                                        rotateIfFarFromLine();
                                }
                                pilot.stop();
                        }
                        else{
                                pilot.stopWheel(Motor.A);
                                pilot.forward();
                                while(!inRange()){
                                	LCD.drawString("Waarde: " + sensor.getNormalizedLightValue(), 0, 0);
                                	LCD.drawString("FarFrom: " + isFarFromLine(), 0, 1);
                                	LCD.drawString("OnLine: " + isOnLine(), 0, 2);
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
                                	LCD.drawString("Waarde: " + sensor.getNormalizedLightValue(), 0, 0);
                                	LCD.drawString("FarFrom: " + isFarFromLine(), 0, 1);
                                	LCD.drawString("OnLine: " + isOnLine(), 0, 2);
                                        pilot.stopWheel(Motor.A);
                                        rotateIfOnLine();
                                }
                                pilot.stop();
                        }
                        else{
                                pilot.stopWheel(Motor.B);
                                pilot.forward();
                                while(!inRange()){
                                	LCD.drawString("Waarde: " + sensor.getNormalizedLightValue(), 0, 0);
                                	LCD.drawString("FarFrom: " + isFarFromLine(), 0, 1);
                                	LCD.drawString("OnLine: " + isOnLine(), 0, 2);
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
                                pilot.rotate(-1);
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
                        return (sensor.getNormalizedLightValue() < (getMin()+5));
                else 
                        return (sensor.getNormalizedLightValue() > (getMax()-5));
        }


		public int getValue() {
			return sensor.getNormalizedLightValue();
		}

		public void start() {
			Thread lineFollowerThread = new Thread(this);
			lineFollowerThread.start();
		}
}
