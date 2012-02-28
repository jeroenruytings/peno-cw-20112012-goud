package pacmansystem.world;

import java.util.Map;


public class World {
	private Map<String, RobotData>_robots;
	
	public Map<String, RobotData> get_robots() {
		return _robots;
	}

	public World(){
		
	}
	
	public RobotData getRobot(String name) {
		return _robots.get(name);
	}
	
}
