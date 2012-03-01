package pacmansystem.ai.robot.fysicalRobot.connector;

public enum Action
{
	STOP, FORWARD, BACKWARD, LEFT, RIGHT, CALIBRATEBLACK, 
	CALIBRATEWHITE, CALIBRATEBROWN, READBARCODE
	// stop = 0, forward = 1, backward = 2, left = 3, right = 4, calibrablack = 5,
	// calibratewhite = 6, Calibratebrown = 7, readbarcode = 8, etc...
	;

	public static Action getActionByOrdinal(int nextInt)
	{
		switch (nextInt)
		{
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
		default:
			return Action.STOP;
		}
	}
}
