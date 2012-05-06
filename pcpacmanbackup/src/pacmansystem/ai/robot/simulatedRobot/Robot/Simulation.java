package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

public class Simulation implements Runnable
{
	
	private Ticker ticker;
	private Robot robot;
	private RobotCommunicator comm;
	private CommandoListener l;
	/**
	 * 
	 * @param robot
	 * @param ticker
	 * @param comm
	 * @param l 
	 */
	public Simulation(Robot robot,Ticker ticker,RobotCommunicator comm, CommandoListener l)
	{
		this.comm = comm;
		this.robot=robot;
		this.ticker=ticker;
		this.l=l;
	}
	@Override
	public void run()
	{
		ticker.start();
		

	}
	public Robot getRobot()
	{
		return robot;
	}
	public Ticker getTicker()
	{
		return ticker;
	}
	public CommandoListener getCl()
	{
		return l;
	}
	public void start()
	{
		new Thread(this).start();
	}
}
