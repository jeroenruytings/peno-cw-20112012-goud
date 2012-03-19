package util.world;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import util.board.Board;

import communicator.be.kuleuven.cs.peno.MessageReceiver;
import communicator.be.kuleuven.cs.peno.MessageSender;
import communicator.parser.Command;

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

	public void start(RobotData me)
	{
		_robots.put(me.getName(), me);
		// send join, wait for join!
		join();
		// wait for 3 other joins or 1 name
		try {
			synchronized (this) {

				this.wait();

			}
		} catch (InterruptedException e) {
			throw new Error("STarting of the world failed for robot:"
					+ me.getName());
		}
		sendName(me.getName());
	}

	public void join()
	{
		try {
			MessageSender.getInstance().sendMessage("JOIN\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void sendName(String name)
	{
		try {
			MessageSender.getInstance().sendMessage(name + " NAME 1.0\n");
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
	public synchronized void register()
	{
		registeredRobots++;
		if (registeredRobots == amountOfRobotsNeeded)
			this.notify();
	}

	/**
	 * 
	 * @param name
	 * @throws InsufficientJoinsException
	 */
	public synchronized void addRobot(String name)
			throws InsufficientJoinsException
	{
		if (!this._robots.containsKey(name)) {
			RobotData r = new RobotData();
			r.setName(name);
			_robots.put(name, r);
		}
		// This is a name command ! this means that shit should be ok:)
		if (_robots.size() == amountOfRobotsNeeded)
			this.notify();
	}

	// public void addRobot(RobotData robot, String name){
	// _robots.put(name, robot);
	// }

	public Board getGlobalBoard()
	{
		return new Board();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (o == rec) {
			Command cmd = ((Command) arg);
			cmd.execute(this);
		}
	}

	public void setRobot(RobotData data, String name)
	{
		_robots.put(name, data);

	}
}

// globalBoard = new Board();
// //TODO: Werk beter uit.
// // Just trying to make is work.
// // --> Dus ge moet ni komen klagen tegen mij (Jannes). K zal dit ooit wel is
// fixen :P
// boolean first = true;
// for(RobotData data:this._robots.values()){
// if (first){
// for (Point p : data.getBoard().getFilledPoints()){
// globalBoard.add(data.getBoard().getPanelAt(p), p);
// }
// first = false;
// }
// else
// globalBoard = BoardUnifier.unify(globalBoard, data.getBoard());
// }
// return globalBoard;
