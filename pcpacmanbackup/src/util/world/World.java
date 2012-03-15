package util.world;

import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import communicator.be.kuleuven.cs.peno.MessageReceiver;
import communicator.parser.Command;

import util.board.Board;
import util.board.operations.BoardUnifier;



public class World implements Observer
{
	private Map<String, RobotData> _robots;
	private int registeredRobots;
	private int amountOfRobotsNeeded = 4;
	private MessageReceiver rec;

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
		try {
			rec = new MessageReceiver();
			rec.addObserver(this);
			Thread t = new Thread(rec);
			t.start();
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
	
	

	private Board globalBoard;
	public Board getGlobalBoard()
	{
//		globalBoard = new Board();
//		//TODO: Werk beter uit.
//		// Just trying to make is work.
//		// --> Dus ge moet ni komen klagen tegen mij (Jannes). K zal dit ooit wel is fixen :P
//		boolean first = true;
//		for(RobotData data:this._robots.values()){
//			if (first){
//				for (Point p : data.getBoard().getFilledPoints()){
//					globalBoard.add(data.getBoard().getPanelAt(p), p);
//				}
//				first = false;
//			}
//			else
//				globalBoard = BoardUnifier.unify(globalBoard, data.getBoard());
//		}
//		return globalBoard;
		return get_robots().get("Goud0").getBoard();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o==rec){
			((Command) arg).execute(this);
		}			
	}

	


}
