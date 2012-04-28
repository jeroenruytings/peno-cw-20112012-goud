package communicator.parser.messages;

import java.awt.Point;

public class RediscoverMessage extends DiscoverMessage {

	public RediscoverMessage(String name, Point coordinate, int n, int e,
			int s, int w) {
		super(name, coordinate, n, e, s, w);
	}
	
	@Override
	public String getKeyword(){
		return "REDISCOVER";
	}

}
