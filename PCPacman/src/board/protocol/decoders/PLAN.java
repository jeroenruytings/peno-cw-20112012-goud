package board.protocol.decoders;

import java.text.ParseException;

import board.protocol.Command;
import board.protocol.Decoder;

public class PLAN extends Decoder {

	protected PLAN(Decoder next) {
		super(next, "PLAN");
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
