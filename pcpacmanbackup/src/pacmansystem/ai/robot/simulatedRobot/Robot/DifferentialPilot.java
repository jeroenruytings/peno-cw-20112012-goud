package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.ticking.Tickable;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

//TODO: implement
public class DifferentialPilot implements Tickable
{
	private interface Movement extends Tickable
	{
		boolean isFinished();
	}

	private class Idle implements Movement
	{

		@Override
		public void tick(Ticker ticker)
		{
			
			// do nothing
		}

		@Override
		public boolean isFinished()
		{
			return true;
		}

	}

	private class Forward implements Movement
	{

		public Forward(int i)
		{
		}

		@Override
		public void tick(Ticker ticker)
		{

		}

		@Override
		public boolean isFinished()
		{
			return false;
		}

	}

	private class Rotate implements Movement
	{

		Rotate(int degrees)
		{

		}

		@Override
		public void tick(Ticker ticker)
		{

		}

		@Override
		public boolean isFinished()
		{

			return false;
		}

	}

	private Movement currentMovement = new Idle();
	private Movement nextMove;
/**
 * 
 * @param speed
 * 	the amount of mm the robot drives each tick.
 */
	public DifferentialPilot(float speed)
	{
		// TODO implement
	}
	
	public void stop()
	{
		setNextMove(new Idle());
	}

	public void rotate(int degrees)
	{
		Movement rotate = new Rotate(degrees);
		setNextMove(rotate);
		waitForCompletion(rotate);
	}

	/**
	 * 
	 * @param i
	 */
	public void travel(int i)
	{
		setNextMove(new Forward(i));

	}

	@Override
	public void tick(Ticker ticker)
	{
		if (nextMove != null) {
			currentMovement = nextMove;
			nextMove = null;
		}
		currentMovement.tick(ticker);
		if (currentMovement.isFinished()) {
			notifyCurrentMethod(currentMovement);
			currentMovement = new Idle();

		}

	}

	private void waitForCompletion(Movement rotate)
	{
		while (!rotate.isFinished())
			synchronized (rotate) {
				try {
					rotate.wait();
				} catch (InterruptedException e) {
				}
			}
	}

	private void setNextMove(Movement move)
	{
		nextMove = move;

	}

	private void notifyCurrentMethod(Movement currentMovement2)
	{
		synchronized (currentMovement2) {

			currentMovement2.notify();

		}
	}

	public void waitForPress()
	{
		// TODO Auto-generated method stub
		
	}

	public void setSpeed(int i)
	{
		
	}

	public void forward()
	{
		// TODO Auto-generated method stub
		
	}

	public void resetTachoCount()
	{
		// TODO Auto-generated method stub
		
	}

	public int getTachoCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public TurningComponent getHead()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
