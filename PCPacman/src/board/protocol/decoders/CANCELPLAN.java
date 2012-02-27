package board.protocol.decoders;

import java.text.ParseException;

import board.CommandCancelPlan;
import board.protocol.Command;
import board.protocol.Decoder;

public class CANCELPLAN extends Decoder {

	public CANCELPLAN(Decoder next) {
		super(next, "CANCELPLAN");}

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
		return new CommandCancelPlan();
	}

}
