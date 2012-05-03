package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.messages.CancelPlanMessage;
import communicator.parser.messages.Message;


public class CancelPlanMessageDecoder extends MessageDecoder<CancelPlanMessage>
{

	public CancelPlanMessageDecoder(MessageDecoder<? extends Message> next)
	{
		super(next, "CANCELPLAN");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public CancelPlanMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new CancelPlanMessage(mes[0]);
	}

}
