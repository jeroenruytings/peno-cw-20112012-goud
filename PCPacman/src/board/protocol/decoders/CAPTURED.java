package board.protocol.decoders;

import java.text.ParseException;

import board.CommandCaptured;
import board.protocol.Command;
import board.protocol.Decoder;

public class CAPTURED extends Decoder{

	public CAPTURED(Decoder next) {
		super(next, "CAPTURED");
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
		return new CommandCaptured();
	}

}
