import lejos.nxt.Motor;
import lejos.robotics.Movement;
import lejos.robotics.TachoMotor;
import lejos.robotics.proposal.DifferentialPilot;


public class ImprovedDifferentialPilot extends DifferentialPilot {

	float unidentifiedForward = 0;
	int currenttachoCount = 0;
	boolean passedUnf = false;
	
	
	public ImprovedDifferentialPilot(float wheelleft, float wheelright,
			float width, TachoMotor leftMotor, TachoMotor rightMotor, boolean b) {
		super(wheelleft, wheelright,width, leftMotor, rightMotor,b);
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
	public Movement rotate(float angle,boolean nonblocking){
		unfFix();
		Message msg;
		if (angle > 0)
			msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.TurnLeft, new SensorValue((byte) angle));
		else
			msg = new Message(Monitor.ActionLogMonitor,ActionLogIdentifier.TurnRight, new SensorValue((byte) angle));
		Communicator.instance().send(msg);
		return super.rotate(angle,nonblocking);
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
		return super.travel(distance);
		
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
