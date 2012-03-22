package communicator.be.kuleuven.cs.peno;

/**
 * An interface that contains some configuration data for the examples. 
 */
public interface Config {
	public static final String USER_NAME = "guest";
	public static final String PASSWORD = "guest";
	public static final String VIRTUAL_HOST = "/";
	// "leuven.cs.kotnet.kuleuven.be"
	public static final String HOST_NAME = "leuven.cs.kotnet.kuleuven.be";
	public static final int PORT = 5672;
	
	// the default exchange that will be used for races during the official demo's
	//RaceExchange
	public static final String EXCHANGE_NAME = "Goud";
	public static final String LAUNCH_ROUTING_KEY = "race.launch";
	
}
