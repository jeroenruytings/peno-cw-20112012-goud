package board;

import java.util.Map;

import system.ProxyRobot;

public class World {
	private Map<String, ProxyRobot>	_robots;
	
	public World(){
		
	}
	
	public ProxyRobot getRobot(String name) {
		return _robots.get(name);
	}
}
