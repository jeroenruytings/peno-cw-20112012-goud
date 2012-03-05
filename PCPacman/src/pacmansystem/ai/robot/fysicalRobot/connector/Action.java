package pacmansystem.ai.robot.fysicalRobot.connector;

public enum Action
{
	STOP, FORWARD, BACKWARD, LEFT, RIGHT, CALIBRATEBLACK, 
	CALIBRATEWHITE, CALIBRATEBROWN, READBARCODE, LIGHTSENSORVALUE
	// stop = 0, forward = 1, backward = 2, left = 3, right = 4, calibrablack = 5,
	// calibratewhite = 6, Calibratebrown = 7, readbarcode = 8, etc...

	;

	public static Action getActionByOrdinal(int nextInt)
	{

		for(Action a : Action.values())
			if(a.ordinal()==nextInt)
				return a;
		return STOP;//XXX: waarom is dit de default actie ...?
	}
	
}
