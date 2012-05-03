package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.messages.CapturedMessage;
import communicator.parser.messages.Message;


public class CapturedMessageDecoder extends MessageDecoder<CapturedMessage>
{

	public CapturedMessageDecoder(MessageDecoder<? extends Message> next)
	{
		super(next, "CAPTURED");
	}

	@Override
	public CapturedMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new CapturedMessage(mes[0]);
		
	}

}
