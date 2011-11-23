


public class GoStraightBehavior extends LeoBehavior {
	
	boolean suppressed = false;

	@Override
	public void action() {
		suppressed = false;
		pilot.setSpeed(540);
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
