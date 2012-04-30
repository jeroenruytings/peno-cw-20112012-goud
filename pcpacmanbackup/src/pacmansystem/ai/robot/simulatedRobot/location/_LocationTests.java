package pacmansystem.ai.robot.simulatedRobot.location;

import org.junit.Before;

import pacmansystem.ai.robot.simulatedRobot.test.CreationTest;

import data.world.RealWorld;

public class _LocationTests
{
	RealWorld w1;
	RealWorld w2;
	RealWorld w3;
	RealWorld w4;
	
	@Before
	public void init()
	{
		w1 = RealWorld.getRealWorld(CreationTest.class.getResource("/resources/testworld.txt").getFile());
		
	}
	
}
