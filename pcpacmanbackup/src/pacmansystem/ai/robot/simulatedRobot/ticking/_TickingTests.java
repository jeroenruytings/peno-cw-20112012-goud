package pacmansystem.ai.robot.simulatedRobot.ticking;

import static org.junit.Assert.*;

import org.junit.Test;

public class _TickingTests
{
	class TickingCounter implements Tickable
	{

		public int ticks=0;

		@Override
		public void tick(Ticker ticker)
		{
			ticks++;
		}
		
	}
	@Test
	public void test0()
	{
		Ticker ticker = new Ticker();
		TickingCounter counter = new TickingCounter();
		ticker.add(counter);
		ticker.start();
		sleep();
		assertTrue(counter.ticks>10);
		assertFalse(counter.ticks>20);
		System.out.println(counter.ticks);
		
	}
	private void sleep()
	{
		try {
			Thread.sleep(120);
		} catch (InterruptedException e) {
		}
	}
}
