package board.protocol.decoders;

import java.text.ParseException;

import board.Simulator;
import board.protocol.Command;
import board.protocol.Decoder;

public class POSITION extends  Decoder{

	protected POSITION(Decoder next) {
		super(next, "POSITION");
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
		// TODO Auto-generated method stub
		return null;
	}
	
}
