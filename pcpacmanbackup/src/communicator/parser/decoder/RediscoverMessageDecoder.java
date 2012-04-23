package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.RediscoverMessage;

public class RediscoverMessageDecoder extends MessageDecoder<RediscoverMessage>{

	protected RediscoverMessageDecoder(MessageDecoder<? extends Message> next,
			String key) {
		super(next, "REDISCOVER");
	}

	@Override
	public RediscoverMessage parse(String message) throws ParseException {
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		String[] coord = mes[2].split(",");
		Point discovered = new Point(Integer.parseInt(coord[0]),
				Integer.parseInt(coord[1]));
		//TODO: draaiing fixen
		return new RediscoverMessage(mes[0], discovered, Byte.parseByte(mes[3]),Byte.parseByte(mes[4]),
				Byte.parseByte(mes[5]),Byte.parseByte(mes[6]));
		
	}
	
	
	

}
