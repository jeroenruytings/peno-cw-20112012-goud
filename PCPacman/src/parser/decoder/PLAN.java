package parser.decoder;

import java.text.ParseException;

import parser.Command;
import parser.Decoder;


public class PLAN extends Decoder {

	protected PLAN(Decoder next) {
		super(next, "PLAN");
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