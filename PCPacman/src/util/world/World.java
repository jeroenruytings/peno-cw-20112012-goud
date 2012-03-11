package util.world;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import util.board.Board;
import util.board.operations.BoardUnifier;



public class World
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
	public void addRobot(String name) throws InsufficientJoinsException
	{
		if (registeredRobots != 4)
			throw new InsufficientJoinsException();
		_robots.put(name, new RobotData());
	}
	
	public void addRobot(RobotData robot, String name){
		_robots.put(name, robot);
	}
	
	private Board globalBoard =new Board();
	
	public Board getGlobalBoard()
	{
		return globalBoard;
	}
	
	public void calcBoard(){
		Board b = globalBoard;
		
		for(RobotData data:this._robots.values())
			for(RobotData data2 : this._robots.values())
			b = BoardUnifier.unify(data2.getBoard(), data.getBoard());
		
	}

}
