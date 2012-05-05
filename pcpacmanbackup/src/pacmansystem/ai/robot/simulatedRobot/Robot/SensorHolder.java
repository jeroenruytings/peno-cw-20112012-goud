package pacmansystem.ai.robot.simulatedRobot.Robot;

import java.util.List;

import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.ticking.Tickable;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;
/**
 * Class to hold the Ultrasonic sensor and the IR sensor.
 * *
 */
public class SensorHolder implements MovingComponent
{

	private UltrasonicSensor ultra;
	private IRSeekerV2 seeker;
	private Robot moving;
	private int angle =0;
	private Movement nextMove;
	public SensorHolder(Robot moving)
	{
		this.moving = moving;
		moving.setSensorHolder(this);
	}
	@Override
	public void tick(Ticker ticker)
	{
		
	}
	public void setUltraSonicSensor(UltrasonicSensor sensor)
	{
		this.ultra = sensor;
	}
	public void setIRSensor(IRSeekerV2 seeker)
	{
		this.seeker=seeker;
	}
	@Override
	public double getDirection()
	{
		return moving.getDirection()+angle;
	}

	@Override
	public Pointf getLocation()
	{
		return null;
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
		
		
	}
	private class Rotate implements Movement
	{
		int degreesLeft;
		// degrees/second
		double degreesdone = 0;
		//double rotationSpeed = (SensorHolder.this.moving.speed() / mmDeg(moving.widht / 2));

		Rotate(int degrees)
		{
			this.degreesLeft = degrees;
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
//			double degrees = rotationSpeed / ticker.getTicksPerSecond();
//			if (degrees > (degreesLeft-degreesdone))
//				degrees = (degreesLeft-degreesdone);
//			degreesdone += degrees;
//			double robotD = moving.getDirection();
//			List<Pointf> conv = Robot.convexAround(moving.getLocation(), robotD
//					+ degrees);
//			if (moving.getView().conflicting(conv)) {
//				// do nothing
//				return;
//			}
//			moving.setDirection(robotD + degrees);

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
			// TODO Auto-generated method stub
			
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

}
