package parser.decoder;

import java.text.ParseException;

import parser.Command;
import parser.Decoder;
import parser.command.CommandCancelPlan;


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
