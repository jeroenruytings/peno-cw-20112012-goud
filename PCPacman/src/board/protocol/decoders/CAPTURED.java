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
		String[] mes = message.split(" ");
		if(!correctKey(message))
			return false;
		return true;
	}

	@Override
	public Command parse(String message) throws ParseException {
		String[] mes = message.split(" ");
		return new CommandCaptured(mes[0]);
	}

}
