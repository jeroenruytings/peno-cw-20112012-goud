package util.world;

import java.util.HashMap;
import java.util.Map;

import util.board.Board;
import util.board.operations.BoardUnifier;



public class World
{
	private Map<String, RobotData> _robots;
	private int registeredRobots;
	private int amountOfRobotsNeeded = 4;
	/**
	 * @return The data of the robots on this world.
	 */
	public Map<String, RobotData> get_robots()
	{
		return _robots;
	}

	public World()
	{
		_robots = new HashMap<String, RobotData>();
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
	public synchronized void addRobot(String name) throws InsufficientJoinsException
	{
		if (registeredRobots != amountOfRobotsNeeded)
			throw new InsufficientJoinsException();
		RobotData r = new RobotData();
		r.setName(name);
		_robots.put(name, r);
		
		if (_robots.size() == amountOfRobotsNeeded)
			this.notify();
	}
	
//	public void addRobot(RobotData robot, String name){
//		_robots.put(name, robot);
//	}
	

	public Board getGlobalBoard()
	{
		Board b = new Board();
		for(RobotData data:this._robots.values())
			b = BoardUnifier.unify(b, data.getBoard());
		return b;
	}
	


}
