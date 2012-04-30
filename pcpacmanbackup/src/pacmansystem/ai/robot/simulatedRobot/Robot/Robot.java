package pacmansystem.ai.robot.simulatedRobot.Robot;

import pacmansystem.ai.robot.simulatedRobot.SIMINFO;
import pacmansystem.ai.robot.simulatedRobot.location.RealWorldView;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Tickable;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

//TODO: implement
public class Robot implements Tickable
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
	private final RealWorldView view;
	private Pointf origin = new Pointf(SIMINFO.PANELWIDTH / 2,
			SIMINFO.PANELHEIGHT / 2);
	private int degrees =0; // The direction the robot is facing aka North at start

	/**
	 * 
	 * @param speed
	 *  The speed of the robot in mm / tick
	 * @param view
	 *  The Realworld view of our simulation, this contains our walls,barcodes & other robots
	 * @param origin
	 * 	The Continu location of the robot.
	 * @param degrees
	 *  The Degrees the robot is away from north,</br> 
	 *  	This means 0 is North and 90 is West
	 * 
	 */
	public Robot(int speed, RealWorldView view,Pointf origin,int degrees)
	{
		if (view == null)
			throw new IllegalArgumentException("Passed in view cannot be null");
		if(origin == null)
			throw new IllegalArgumentException("passed in origin cannot be null");
		this.view = view;
		setDegrees(degrees+90);//hack to make sure 0 is north.
	}
	/**
	 * Generates a vector in the direction the robot is currently facing of length 1.
	 * @return
	 */
	public Pointf getDirection()
	{
		return new Pointf(Math.cos(degrees),Math.sin(degrees));
	}
	/**
	 * Sets the degree value % 360
	 * @param degrees
	 */
	private void setDegrees(int degrees)
	{
		this.degrees=degrees%360;
	}

	public Pointf getCenterPoint()
	{
		return origin;

	}

	public RealWorldView getView()
	{
		return view;
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
