package board.protocol.decoders;

import java.text.ParseException;

import board.protocol.Command;
import board.protocol.Decoder;

public class PONG extends Decoder {

	protected PONG(Decoder next) {
		super(next, "PONG");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDecode(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Command parse(String message) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}