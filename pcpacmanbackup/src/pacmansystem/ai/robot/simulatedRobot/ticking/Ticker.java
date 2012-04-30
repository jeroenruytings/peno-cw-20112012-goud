package pacmansystem.ai.robot.simulatedRobot.ticking;

import java.util.ArrayList;
import java.util.Collection;

public class Ticker
{
	private long ticks = 0;
	private Collection<Tickable> tickables_ = new ArrayList<Tickable>();
	private boolean running = false;

	public void add(Tickable tick)
	{
		synchronized (tickables_) {
			tickables_.add(tick);
		}
	}

	public void start()
	{
		if (running)
			return;
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				Ticker.this.run();
			}
		}).start();
		running = true;
	}

	/**
	 * Tick every 10 ms;
	 */
	private void run()
	{
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			ticks++;
			synchronized (tickables_) {

				for (Tickable tick : tickables_)
					tick.tick(this);

			}
		}
	}

	public long getTicks()
	{
		return ticks;
	}
}
