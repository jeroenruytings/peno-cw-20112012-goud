import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class MuurUpdater implements Runnable{
    UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S2);
    static int aheadDistance;
    static int leftDistance;
    static int rightDistance;
    static boolean newValuesAhead = true;
    static boolean newValuesRight = true;
    static boolean newValuesLeft = true;
    
    
    public void start() {
        Thread muurData = new Thread(this);
        muurData.start();
    }
    
    public void run() {
        while(true){
        	Message mes = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte)sonic.getDistance()));
        	Communicator.instance().send(mes);
            setAheadDistance(sonic.getDistance());
            newValuesAhead = true;
            Motor.C.rotate(90);
            setRightDistance(sonic.getDistance());
            newValuesRight = true;
            Motor.C.rotate(-90);
            setAheadDistance(sonic.getDistance());
            newValuesAhead = true;
            Motor.C.rotate(-90);
            setLeftDistance(sonic.getDistance());
            newValuesLeft = true;
            Motor.C.rotate(90);
            newValuesAhead = true;
        }
    }
    
    public int getAheadDistance() {
        return MuurUpdater.aheadDistance;
    }
    public void setAheadDistance(int aheadDistance) {
        MuurUpdater.aheadDistance = aheadDistance;
    }
    public int getLeftDistance() {
        return MuurUpdater.leftDistance;
    }
    public void setLeftDistance(int leftDistance) {
        MuurUpdater.leftDistance = leftDistance;
    }
    public int getRightDistance() {
        return MuurUpdater.rightDistance;
    }
    public void setRightDistance(int rightDistance) {
        MuurUpdater.rightDistance = rightDistance;
    }
    
}