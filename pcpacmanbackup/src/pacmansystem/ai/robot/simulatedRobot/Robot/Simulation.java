package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

public class Simulation implements Runnable
{
	
	private Ticker ticker;
	private Robot robot;
	private RobotCommunicator comm;
	/**
	 * 
	 * @param robot
	 * @param ticker
	 * @param comm
	 */
	public Simulation(Robot robot,Ticker ticker,RobotCommunicator comm)
	{
		this.comm = comm;
		this.robot=robot;
		this.ticker=ticker;
	}
	@Override
	public void run()
	{
		ticker.start();
		
		comm.receiveCommando();
	}
	public Robot getRobot()
	{
		return robot;
	}
	public Ticker getTicker()
	{
		return ticker;
	}
}
