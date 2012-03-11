package interfaces.pacmancomponents;

import util.board.Board;
import util.world.RobotData;
import util.world.World;

public class WorldDisplayer extends BoardDisplay {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private World myworld;
	
	public WorldDisplayer(World w) {
		super();
		myworld = w;
		for (RobotData r : w.get_robots().values())
			addRobotData(r);
	}
	
	public World getWorld(){
		return myworld;
	}

	@Override
	public Board getBoard() {
		return myworld.getGlobalBoard();
	}

}
