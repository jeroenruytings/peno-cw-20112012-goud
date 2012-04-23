package pacmansystem.ai.robot.simulatedRobot.Robot;

import java.io.IOException;

public class Button
{

	public static final Button ENTER = null;

	public void waitForPressAndRelease()
	{
		System.out.println("Pres enter to advance.");
		try {
			System.in.read();
		} catch (IOException e) {
			
		}
		
		
	}

}
