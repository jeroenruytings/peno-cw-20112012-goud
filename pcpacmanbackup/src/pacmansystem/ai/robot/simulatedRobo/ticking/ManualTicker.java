package pacmansystem.ai.robot.simulatedRobo.ticking;

public class ManualTicker extends Ticker
{

	public ManualTicker(int ticsPerSecond){
		super();
	}
	
	@Override
	public void start()
	{
		//The timer is not started.
	}
	
	public void tickAll()
	{
		this.ticks++;
		for(Tickable t:this.tickables_)
			t.tick(this);
	}
}
