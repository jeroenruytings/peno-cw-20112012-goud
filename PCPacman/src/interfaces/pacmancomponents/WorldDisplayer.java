package interfaces.pacmancomponents;

import util.world.RobotData;
import util.world.World;

public class WorldDisplayer extends BoardDisplay {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorldDisplayer() {
		super(null);
	}
	
	public void setWorld(World w){
		setBoard(w.getGlobalBoard());
		for (RobotData r : w.get_robots().values())
			addRobotData(r);
	}

}
