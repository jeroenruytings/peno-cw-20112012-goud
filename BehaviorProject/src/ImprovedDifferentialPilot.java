import lejos.nxt.Motor;
import lejos.robotics.Movement;
import lejos.robotics.TachoMotor;
import lejos.robotics.proposal.DifferentialPilot;


public class ImprovedDifferentialPilot extends DifferentialPilot {

	float unidentifiedForward = 0;
	int currenttachoCount = 0;
	boolean passedUnf = false;
	
	
	public ImprovedDifferentialPilot(float wheelDiameter, float trackWidth,
			float f, TachoMotor leftMotor, TachoMotor rightMotor, boolean b) {
		super(wheelDiameter, trackWidth, leftMotor, rightMotor);
	}
	
	public ImprovedDifferentialPilot(float wheelDiameter, float trackWidth,
			float f, Motor a, Motor b, boolean b2) {
		super(wheelDiameter,trackWidth,f,a,b,b2);
	}

	@Override
	public Movement rotate(float angle){
		unfFix();
		Message msg;
		if (angle > 0)
			msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.TurnLeft, new SensorValue((byte) angle));
		else
			msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.TurnRight, new SensorValue((byte) angle));
		Communicator.instance().send(msg);
		return super.rotate(angle);
	}
	
	@Override
	public Movement travel(float distance){
		Message msg;
		unfFix();
		if (distance < 255)
		 msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.FORWARDMILIMETERS, new SensorValue((byte) distance));
		else
			msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.Forward, new SensorValue((byte) (distance/ 100)));
		Communicator.instance().send(msg);
		return travel(distance);
		
	}
	
	@Override
	public void forward(){
		unfFix();
		Message	msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.UNSPECIFIEDFORWARD,new SensorValue((byte)0));
		Communicator.instance().send(msg);
		passedUnf = true;
		currenttachoCount = this.getLeft().getTachoCount();
		
		super.forward();
	}
	
	private void unfFix(){
		if (!passedUnf)
			return;
		Message msg;
		double tmpDist = ((this.getLeft().getTachoCount() - currenttachoCount) * 2 * Math.PI) / 360;
		if (tmpDist < 255)	
				msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.FORWARDMILIMETERS,new SensorValue((byte)tmpDist));
		else
				msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.Forward,new SensorValue((byte)(tmpDist / 100)));
		Communicator.instance().send(msg);
		currenttachoCount = 0;
		passedUnf = false;
	}
	

	
}
