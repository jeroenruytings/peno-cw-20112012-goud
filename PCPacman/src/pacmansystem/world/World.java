package pacmansystem.world;


import java.io.IOException;
import java.util.Map;

import be.kuleuven.cs.peno.MessageReceiver;

public class World extends RealWorld
{
	private Map<String, RobotData> _robots;
	private int registeredRobots;

	/**
	 * @return The data of the robots on this world.
	 */
	public Map<String, RobotData> get_robots()
	{
		return _robots;
	}

	public World()
	{
		MessageReceiver rec;
		try {
			rec = new MessageReceiver(this);
			rec.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RobotData getRobot(String name)
	{
		return _robots.get(name);
	}
	/**
	 * The join command executes this
	 */
	public void register()
	{
		registeredRobots++;
	}
	/**
	 * 
	 * @param name
	 * @throws InsufficientJoinsException
	 */
	public void addRobot(String name) throws InsufficientJoinsException
	{
		if (registeredRobots != 4)
			throw new InsufficientJoinsException();
		_robots.put(name, new RobotData());
	}

}
