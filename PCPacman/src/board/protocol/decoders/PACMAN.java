package board.protocol.decoders;

import java.awt.Point;
import java.text.ParseException;

import board.CommandPacman;
import board.protocol.Command;
import board.protocol.Decoder;

public class PACMAN extends Decoder {

	protected PACMAN(Decoder next) {
		super(next, "PACMAN");
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
		String[] coordinate = mes[2].split(",");
		Point coord= new Point(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]));
		return new CommandPacman(mes[0], coord);
	}


}
