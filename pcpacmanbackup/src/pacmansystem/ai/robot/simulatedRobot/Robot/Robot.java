package pacmansystem.ai.robot.simulatedRobot.Robot;

import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.translate;

import java.util.ArrayList;
import java.util.List;

import pacmansystem.ai.robot.simulatedRobot.SIMINFO;
import pacmansystem.ai.robot.simulatedRobot.location.RealWorldView;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.point.Pointfs;
import pacmansystem.ai.robot.simulatedRobot.ticking.Ticker;

//TODO: implement
public class Robot implements MovingComponent
{
	public static final float widht = 150;
	public static final float height = 250;
	private final RealWorldView view;
	private Pointf origin ;
	private double degrees = 0; // The direction the robot is facing aka North at
								// start
	private LightSensor lightSensor = null;
	private SensorHolder movingComponent;
	private TouchSensor touch;
	private SimulatedPilot pilot;

	/**
	 * 
	 * @param speed
	 *            The speed of the robot in mm / tick
	 * @param view
	 *            The Realworld view of our simulation, this contains our
	 *            walls,barcodes & other robots
	 * @param origin
	 *            The Continu location of the robot.
	 * @param degrees
	 *            The Degrees the robot is away from north,</br> This means 0 is
	 *            North and 90 is West
	 * 
	 */
	Robot(int speed, RealWorldView view, Pointf origin, int degrees)
	{
		if (view == null)
			throw new IllegalArgumentException("Passed in view cannot be null");
		if (origin == null)
			throw new IllegalArgumentException(
					"passed in origin cannot be null");
		this.view = view;
		setDegrees(degrees + 90);// hack to make sure 0 is north.
		this.pilot = new SimulatedPilot(this,speed);
		this.origin=origin;
	}

	/**
	 * Gets the number of degrees this component is away from 0 degrees ( to the right)
	 * ahead.
	 * 
	 * @return
	 */
	public double getDirection()
	{
		return degrees;
	}

	private void setDegrees(double degrees)
	{
//		while(degrees>360)
//			degrees-=360;
		this.degrees=degrees;
	}

	public RealWorldView getView()
	{
		return view;
	}

	@Override
	public void tick(Ticker ticker)
	{

	}

	public void waitForPress()
	{
		// TODO Auto-generated method stub

	}

	public SimulatedPilot getPilot()
	{
		return pilot;
	}

	/**
	 * MUST ONLY BE CALLED BY LIGHTSENSOR,MAKES DOUBLE BIND BETWEEN THE 2
	 * OBJECTS
	 * 
	 * @param object
	 */
	void setLightSensor(LightSensor lightSensor)
	{
		if (this.lightSensor != null)
			throw new IllegalStateException(
					"Robot's lightsensor can only be set once");
		this.lightSensor = lightSensor;
	}

	/**
	 * Should only be called by Senshorholder
	 * 
	 * @param movingComponent
	 */
	public void setSensorHolder(SensorHolder movingComponent)
	{

		this.movingComponent = movingComponent;
	}

	@Override
	public Pointf getLocation()
	{
		return origin;
	}
	void setLocation(Pointf location)
	{
		this.origin=location;
	}

	public SensorFacade getSensors()
	{
		return new SensorFacade()
		{

			@Override
			public UltrasonicSensor getUltraSonicSensor()
			{
				return Robot.this.movingComponent.getUltraSonicSensor();
			}

			@Override
			public TouchSensor getTouchSensor()
			{
				// TODO Auto-generated method stub
				return Robot.this.touch;
			}

			@Override
			public LightSensor getLightSensor()
			{
				return lightSensor;
			}

			@Override
			public IRSeekerV2 getInfraRedSensor()
			{
				return Robot.this.movingComponent.getIrSeekerV2();
			}
		};
	}

	public SensorHolder getHead()
	{
		return this.movingComponent;
	}

	public void setTouch(TouchSensor touch2)
	{
		this.touch = touch2;
	}

	public static List<Pointf> convexAround(Pointf center, double d)
	{
		List<Pointf> rv = new ArrayList<Pointf>();

		Pointf widthVector = new Pointf(widht, 0);
		Pointf heightVector = new Pointf(0, height);
		Pointf toOrigin = new Pointf(-widht / 2, -height / 2);
		Pointf origin = translate(center, toOrigin);
		rv.add(origin);
		rv.add(translate(origin, heightVector));
		rv.add(translate(origin, translate(widthVector, heightVector)));
		rv.add(translate(origin, widthVector));
		return Pointfs.rotate(rv, origin, d);
	}

	public void setDirection(double d)
	{
		this.degrees=d;
		
	}
	public static void main(String[] args)
	{
	System.out.println(	Pointfs.fromDegrees(360+90+360));
	}
}
