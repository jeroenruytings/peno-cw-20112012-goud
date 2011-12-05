import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Stopwatch;

public class MuurUpdater implements Runnable{
    static UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S2);
    static int aheadDistance;
    static int leftDistance;
    static int rightDistance;
    static boolean newValuesAhead = true;
    static boolean newValuesRight = true;
    static boolean newValuesLeft = true;
    Stopwatch sw = new Stopwatch();
    
    
    public void start() {
        Thread muurData = new Thread(this);
        muurData.start();
    }
    
    public void run() {
        while(true){
        	aheadDistance = sonic.getDistance();
        	Message mes = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte)aheadDistance));
        	Communicator.instance().send(mes);
        	
        	mes = new Message(Monitor.SensorMonitor, SensorIdentifier.LightSensor, new SensorValue((byte)(LeoBehavior.lightSensor.readRawValue() / 4)));
        	Communicator.instance().send(mes);
        	
        	//rotateAndUpdate();
        }
    }

	private void rotateAndUpdate() {
		Message mes;
		sw.reset();
		while(sw.elapsed() < 750){
		    setAheadDistance(sonic.getDistance());
		    newValuesAhead = true;
		}
		mes = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte)sonic.getDistance()));
		Communicator.instance().send(mes);
		
		Motor.C.rotate(90);
		
		setRightDistance(sonic.getDistance());
		newValuesRight = true;
		mes = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte)sonic.getDistance()));
		Communicator.instance().send(mes);
		
		Motor.C.rotate(-90);
		
		sw.reset();
		while(sw.elapsed() < 750){
		    setAheadDistance(sonic.getDistance());
		    newValuesAhead = true;
		}
		mes = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte)sonic.getDistance()));
		Communicator.instance().send(mes);
		
		Motor.C.rotate(-90);
		
		setLeftDistance(sonic.getDistance());
		newValuesLeft = true;
		
		mes = new Message(Monitor.SensorMonitor, SensorIdentifier.UltrasonicSensor, new SensorValue((byte)sonic.getDistance()));
		Communicator.instance().send(mes);
		Motor.C.rotate(90);
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