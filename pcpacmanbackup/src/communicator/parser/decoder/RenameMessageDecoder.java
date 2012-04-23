package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.RenameMessage;

public class RenameMessageDecoder extends MessageDecoder<RenameMessage> {
	
	protected RenameMessageDecoder(MessageDecoder<? extends Message> next)
	{
		super(next, "RENAME");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public RenameMessage parse(String message) throws ParseException
	{
		if (!canDecode(message))
			throw new ParseException("exc", 0);
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new RenameMessage(mes[0], mes[2]);
	}

}
