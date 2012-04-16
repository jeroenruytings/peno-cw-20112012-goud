package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.Decoder;
import communicator.parser.messages.Message;
import communicator.parser.messages.PacmanMessage;


public class PACMAN extends Decoder
{

	protected PACMAN(Decoder next)
	{
		super(next, "PACMAN");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public Message parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		String[] coordinate = mes[2].split(",");
		Point coord = new Point(Integer.parseInt(coordinate[0]),
				Integer.parseInt(coordinate[1]));
		return new PacmanMessage(mes[0], coord);
	}

}
