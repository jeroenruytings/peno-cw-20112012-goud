package board.protocol.decoders;

import java.awt.Point;
import java.text.ParseException;

import board.CommandDiscover;
import board.protocol.Command;
import board.protocol.Decoder;

public class DISCOVER  extends Decoder {

	public DISCOVER(Decoder next) {
		super(next, "DISCOVER");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDecode(String message) {
		String[] mes = message.split(" ");
		if(!correctKey(message))
			return false;
		return true;
	}

	@Override
	public Command parse(String message) throws ParseException {
		//TODO: Does the message contain "\n"?
		String[] mes = message.split(" ");
		String[] coord = mes[2].split(",");
		Point discovered = new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
		return new CommandDiscover(mes[0],discovered,Byte.parseByte(mes[3]),Byte.parseByte(mes[4]),Byte.parseByte(mes[5]), Byte.parseByte(mes[6]));
	}

}
