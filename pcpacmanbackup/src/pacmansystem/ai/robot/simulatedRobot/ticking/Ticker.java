package pacmansystem.ai.robot.simulatedRobot.ticking;

import java.util.ArrayList;
import java.util.Collection;

public class Ticker
{
	private long ticks =0;
	private Collection<Tickable> tickables_=new ArrayList<Tickable>();
	public void add(Tickable tick)
	{
		tickables_.add(tick);
	}
	private void start()
	{
		while(true)
		{
			ticks++;
			for(Tickable tick:tickables_)
				tick.tick(this);
		}
	}
}
