package parser.decoder;

import java.text.ParseException;

import parser.Command;
import parser.Decoder;
import parser.command.CommandName;


public class NAME extends Decoder{

	
	protected NAME(Decoder next) {
		super(next, "NAME");
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
		if(!canDecode(message))
			throw new ParseException("exc", 0);
		String[] mes= message.split(" ");
		return new CommandName(mes[0],new Integer(mes[2]));
	}

}
