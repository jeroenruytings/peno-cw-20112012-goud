package board.protocol.decoders;

import java.awt.Point;
import java.text.ParseException;

import board.CommandPosition;
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

	/**
	 * Create a Command object for the given message.
	 */
	@Override
	public CommandPosition parse(String message) throws ParseException {
		String[] mes = message.split(" ");
		String[] coord = mes[2].split(",");
		return new CommandPosition(mes[0], new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])));
	}
	
}
