package pacmansystem.world;

import java.util.Map;

public class World extends RealWorld
{
	private Map<String, RobotData> _robots;

	/**
	 * @return The data of the robots on this world.
	 */
	public Map<String, RobotData> get_robots()
	{
		return _robots;
	}

	public World()
	{

	}

	public RobotData getRobot(String name)
	{
		return _robots.get(name);
	}

}
