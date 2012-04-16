package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.Decoder;
import communicator.parser.messages.CapturedMessage;


public class CAPTURED extends Decoder
{

	public CAPTURED(Decoder next)
	{
		super(next, "CAPTURED");
	}

	@Override
	public boolean canDecode(String message)
	{
		return false;
//		if (!correctKey(message))
//			return false;
//		return true;
	}

	@Override
	public CapturedMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		return new CapturedMessage(mes[0]);
		
	}

}
