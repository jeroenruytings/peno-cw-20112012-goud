package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.PositionMessage;


public class PositionMessageDecoder extends MessageDecoder<PositionMessage>
{

	protected PositionMessageDecoder(MessageDecoder<? extends Message> next)
	{
		super(next, "POSITION");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	/**
	 * Create a Command object for the given message.
	 */
	@Override
	public PositionMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		String[] coord = mes[2].split(",");
		return new PositionMessage(mes[0], new Point(
				Integer.parseInt(coord[0]), Integer.parseInt(coord[1])));
	}

}
