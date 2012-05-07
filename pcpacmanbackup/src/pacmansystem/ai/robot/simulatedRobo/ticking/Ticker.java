package pacmansystem.ai.robot.simulatedRobo.ticking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Timer;

public class Ticker
{
	Timer timer ;
	private  int _tickspersecond = 10;
	protected long ticks = 0;
	protected Collection<Tickable> tickables_ = new ArrayList<Tickable>();
	private boolean running = false;
	ActionListener listener;
	private long now;
	private long newNow;
	public Ticker()
	{
		this._tickspersecond = 10;
		 listener = new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				now = newNow;
				newNow = System.currentTimeMillis();
				Ticker.this.tickAll();
				
			}
		};
		this.timer = new Timer(100,listener);
	}
	protected void tickAll()
	{
		for(Tickable t : tickables_)
			t.tick(this);
	}
	public void add(Tickable tick)
	{
		synchronized (tickables_) {
			tickables_.add(tick);
		}
	}

	public void start()
	{
		timer.start();
	}



	public long getTicks()
	{
		return ticks;
	}
	public int getTicksPerSecond()
	{
		return 1;
	}
	public void setTicksPerSecond(int t)
	{
		this._tickspersecond=t;
	}

}
