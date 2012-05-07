package pacmansystem.ai.robot.simulatedRobo.Robot;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobo.point.Pointf;
import pacmansystem.ai.robot.simulatedRobo.point.Pointfs;
import pacmansystem.ai.robot.simulatedRobo.ticking.Tickable;
import pacmansystem.ai.robot.simulatedRobo.ticking.Ticker;

/**
 * Class to hold the Ultrasonic sensor and the IR sensor. *
 */
public class SensorHolder implements MovingComponent
{

	private UltrasonicSensor ultra;
	private IRSeekerV2 seeker;
	private Robot moving;
	private double angle = 0;
	private Movement nextMove;
	private Movement currentMovement = new Idle();

	public SensorHolder(Robot moving)
	{
		this.moving = moving;
		moving.setSensorHolder(this);
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

	public void setUltraSonicSensor(UltrasonicSensor sensor)
	{
		this.ultra = sensor;
	}

	public void setIRSensor(IRSeekerV2 seeker)
	{
		this.seeker = seeker;
	}

	@Override
	public double getDirection()
	{
		return moving.getDirection() + angle;
	}

	@Override
	public Pointf getLocation()
	{
		return moving.getLocation();//Pointfs.translate(moving.getLocation(), Pointfs.multiply(
				//Pointfs.fromDegrees(moving.getDirection()), 50));
	}

	public UltrasonicSensor getUltraSonicSensor()
	{
		return ultra;
	}

	public IRSeekerV2 getIrSeekerV2()
	{
		return seeker;
	}

	public void rotate(int i)
	{
		this.nextMove = new Rotate(i);
		waitForCompletion(nextMove);

	}

	private class Rotate implements Movement
	{
		int degreesLeft;
		// degrees/second
		double degreesdone = 0;
		private double rotationSpeed = 5f;
		private boolean neg;

		Rotate(int degrees)
		{
			this.degreesLeft = degrees;
			if (degreesLeft < 0) {
				this.neg = true;
				degreesLeft = -degreesLeft;
			}
		}

		@Override
		public boolean isFinished()
		{

			return degreesLeft <= degreesdone;
		}

		@Override
		public void tick(Ticker ticker)
		{

			double degrees = rotationSpeed / ticker.getTicksPerSecond();
			if (degrees > (degreesLeft - degreesdone))
				degrees = (degreesLeft - degreesdone);
			degreesdone += degrees;
			if (neg)
				SensorHolder.this.angle -= degrees;
			else
				SensorHolder.this.angle += degrees;
		}

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

		}

	}

	private interface Movement extends Tickable
	{
		boolean isFinished();
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

	private void notifyCurrentMethod(Movement movement)
	{
		synchronized (movement) {

			movement.notify();

		}
	}

	public int getTacho()
	{
		return (int) angle;
	}

}
