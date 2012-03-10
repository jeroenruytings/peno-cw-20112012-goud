package interfaces.pacmancomponents;

import util.world.RobotData;
import util.world.World;

public class WorldDisplayer extends BoardDisplay {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private World myworld;
	
	public WorldDisplayer(World w) {
		super(w.getGlobalBoard());
		myworld = w;
	}
	
	public void setWorld(World w){
		myworld = w;
		setBoard(w.getGlobalBoard());
		for (RobotData r : w.get_robots().values())
			addRobotData(r);
	}
	
	public World getWorld(){
		return myworld;
	}

}
