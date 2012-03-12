package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.Command;
import communicator.parser.Decoder;
import communicator.parser.command.CommandDiscover;


public class DISCOVER extends Decoder
{

	public DISCOVER(Decoder next)
	{
		super(next, "DISCOVER");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public Command parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		String[] coord = mes[2].split(",");
		Point discovered = new Point(Integer.parseInt(coord[0]),
				Integer.parseInt(coord[1]));
		//TODO: draaiing fixen
		return new CommandDiscover(mes[0], discovered, Byte.parseByte(mes[3]),Byte.parseByte(mes[4]),
				Byte.parseByte(mes[5]),Byte.parseByte(mes[6]));
	}

}
