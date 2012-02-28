package pacmansystem.ai.robot.fysicalRobot.connector;

public class NullMonitor extends LeoMonitor
{

	public NullMonitor(LeoMonitor next)
	{
		super(next);
	}

	@Override
	public void accept(byte[] message)
	{
		// TODO Auto-generated method stub

	}

}
