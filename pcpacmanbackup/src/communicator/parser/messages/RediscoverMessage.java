package communicator.parser.messages;

import java.awt.Point;

public class RediscoverMessage extends DiscoverMessage {

	public RediscoverMessage(String name, Point coordinate, int i, int j,
			int k, int l) {
		super(name, coordinate, i, j, k, l);
	}
	
	@Override
	public String getKeyword(){
		return "REDISCOVER";
	}

}
