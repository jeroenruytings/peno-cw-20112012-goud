


public class GoStraightBehavior extends LeoBehavior {
	
	boolean suppressed = false;

	@Override
	public void action() {
		Communicator.instance().send(
				new Message(Monitor.BehaviorMonitor,
						BehaviourIdentifier.GoStraigth,
						BehaviourReason.NOREASON));
		suppressed = false;
		pilot.setSpeed(720);//720 normaal
		pilot.forward();
		while(!suppressed){
			Thread.yield();
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	@Override
	public boolean takeControl() {
		return true;
	}
	

}
