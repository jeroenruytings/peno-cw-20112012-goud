package pacmansystem.ai.robot.simulatedRobo.ticking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.Timer;

public class Ticker
{
	Timer timer ;
	private  int _tickspersecond = 10;
	protected long ticks = 0;
	protected List<Tickable> tickables_ = new ArrayList<Tickable>();
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
		this.timer = new Timer(10,listener);
	}
	protected void tickAll()
	{
		
		for(Tickable t : sort(tickables_))
			t.tick(this);
	}
	private Collection<Tickable> sort(List<Tickable> tickables_2)
	{
		
		return tickables_2;
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
