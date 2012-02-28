package pacmansystem.ai.robot.fysicalRobot.connector;

public enum Action {
	STOP,FORWARD,BACKWARD, LEFT, RIGHT, CALIBRATEBLACK,
	CALIBRATEWHITE, CALIBRATEBROWN, READBARCODE
	//stop = 0. forward = 4, backward = 5, calibrablack = 6, 
	//calibratewhite = 7, Calibratebrown = 8, readbarcode = 9, etc...
;

	public static Action getActionByOrdinal(int nextInt) {
		switch (nextInt){
		case 0:
			return Action.STOP;
		case 1:
			return Action.FORWARD;
		case 2:
			return Action.BACKWARD;
		case 3:
			return Action.LEFT;
		case 4:
			return Action.RIGHT;
			default:return Action.STOP;
		}
	}
}
