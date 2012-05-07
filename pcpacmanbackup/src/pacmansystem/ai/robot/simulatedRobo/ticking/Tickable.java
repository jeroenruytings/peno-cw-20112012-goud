package pacmansystem.ai.robot.simulatedRobo.ticking;

public interface Tickable 
{
	public void tick(Ticker ticker);
	public int importance();
}
