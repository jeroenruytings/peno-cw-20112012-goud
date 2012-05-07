package pacmansystem.ai.robot.simulatedRobo.Robot;

import static pacmansystem.ai.robot.simulatedRobo.point.Pointfs.multiply;
import static pacmansystem.ai.robot.simulatedRobo.point.Pointfs.translate;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobo.point.Pointf;
import pacmansystem.ai.robot.simulatedRobo.point.Pointfs;
import pacmansystem.ai.robot.simulatedRobo.ticking.Tickable;
import pacmansystem.ai.robot.simulatedRobo.ticking.Ticker;

public class SimulatedPilot implements Tickable
{
	private final static double wheelbase = 54.5f;

	public double wheelbase()
	{
		return wheelbase;
	}

	private Robot robot;
	private double speed;

	private Movement currentMovement = new Idle();
	private Movement nextMove;
	private double tachoCount=0;

	/**
	 * gets the speed in mm/s
	 */
	public final double speed()
	{
		return (Math.toRadians(speed) * wheelbase / 2);
	}

	/**
	 * Speed per tick
	 * 
	 * @return
	 */
	public final double speed(int ticksPerSecond)
	{
		return speed() / ticksPerSecond;
	}

	public SimulatedPilot(Robot view, float speed)
	{
		this.robot = view;
		this.speed = speed;

	}

	private interface Movement extends Tickable
	{
		boolean isFinished();
	}

	private class Idle implements Movement
	{

		@Override
		public boolean isFinished()
		{
			return true;
		}

		@Override
		public void tick(Ticker ticker)
		{
			// TODO Auto-generated method stub
			
		}

	}

	private class Forward implements Movement
	{
		private double toTravel = 0;
		private double traveled = 0;
		private boolean min;
		
		public Forward(float i)
		{
			this.toTravel = i;
			if(toTravel<0)
				{
				min=true;
				toTravel=-toTravel;
				}
				
		}

		@Override
		public boolean isFinished()
		{
			return toTravel -traveled== 0;
		}

		@Override
		public void tick(Ticker ticker)
		{
			double travelDistance = speed(ticker.getTicksPerSecond());
			if (travelDistance > (toTravel-traveled))
				travelDistance =(toTravel-traveled);
			traveled  += travelDistance;
			Pointf here = robot.getLocation();
			if(min)
				tachoCount-=travelDistance;
			else
				tachoCount+=travelDistance;
			Pointf there;
			if(min)
				 there= translate(
					here,
					multiply(Pointfs.fromDegrees(robot.getDirection()),
							-(float) travelDistance));
			else
				 there= translate(
							here,
							multiply(Pointfs.fromDegrees(robot.getDirection()),
									(float) travelDistance));
			// now we must check if hte robot can be placed in the new position
			if (robot.getView().conflicting(
					Robot.convexAround(there, robot.getDirection()))) {
				return;
				// shit went bananas for now all is well;
			}
			robot.setLocation(there);

		}

	}

	private class Rotate implements Movement
	{
		int degreesLeft;
		// degrees/second
		double degreesdone = 0;
		double rotationSpeed = (SimulatedPilot.this.speed() / mmDeg(robot.widht / 2));
		private boolean min = false;

		Rotate(int degrees)
		{
			this.degreesLeft = -degrees;
			if(degreesLeft<0)
			{
				degreesLeft= - degreesLeft;
				min =true;
			}
		}

		private double mmDeg(float width)
		{
			return Math.toRadians(1) * width;
		}

		@Override
		public boolean isFinished()
		{

			return degreesLeft <=degreesdone;
		}

		@Override
		public void tick(Ticker ticker)
		{
			double degrees = rotationSpeed / ticker.getTicksPerSecond();
			if (degrees > (degreesLeft-degreesdone))
				degrees = (degreesLeft-degreesdone);
			degreesdone += degrees;
			double robotD = robot.getDirection();
			List<Pointf> conv = Robot.convexAround(robot.getLocation(), robotD
					+ degrees);
			if (robot.getView().conflicting(conv)) {
				// do nothing
				return;
			}
			if(!min)
				robot.setDirection(robotD + degrees);
			else
				robot.setDirection(robotD-degrees);
		}

	}

	public void stop()
	{
		this.currentMovement = new Idle();
		setNextMove(new Idle());
	}

	public void rotate(int degrees)
	{
		Movement rotate = new Rotate(degrees);
		setNextMove(rotate);
		waitForCompletion(rotate);
	}

	/**
	 * Moves i in mm. will not return untill movement is complete
	 * 
	 * @param i
	 */
	public void travel(int i)
	{
		System.out.println("moving from "+robot.getLocation());
		Movement forward = new Forward(i);
		setNextMove(forward);
		waitForCompletion(forward);
		System.out.println("moved to"+robot.getLocation());
	}

	private void waitForCompletion(Movement movement)
	{
		while (!movement.isFinished())
			synchronized (movement) {
				try {
					movement.wait();
				} catch (InterruptedException e) {
				}
			}
	}

	private void setNextMove(Movement move)
	{
		nextMove = move;

	}

	private void notifyCurrentMethod(Movement movement)
	{
		synchronized (movement) {

			movement.notify();

		}
	}

	/**
	 * In degrees per second.
	 * 
	 * @param i
	 */
	public void setSpeed(int i)
	{
		this.speed = i;
	}

	public void forward()
	{
		setNextMove(new Forward(Integer.MAX_VALUE));
	}

	public void resetTachoCount()
	{
		tachoCount=0;

	}
	/**
	 * 
	 * @return
	 */
	public int getTachoCount()
	{
		if(tachoCount<0)
			return 0;
		return (int) (360d*tachoCount/wheelCircumfrence());
	}

	private double wheelCircumfrence()
	{
		return wheelbase*Math.PI;
	}

	@Override
	public void tick(Ticker ticker)
	{
		if (currentMovement instanceof Idle)
			if (nextMove != null)
				currentMovement = nextMove;
		currentMovement.tick(ticker);
		if (currentMovement.isFinished()) {
			notifyCurrentMethod(currentMovement);
			currentMovement = new Idle();
		}
	}

}
