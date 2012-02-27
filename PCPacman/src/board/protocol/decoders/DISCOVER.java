package board.protocol.decoders;

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
		return new CommandDiscover();
	}

}
