package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.UndoBarcodeMessage;

public class UndoBarcodeMessageDecoder extends MessageDecoder<UndoBarcodeMessage>{
	
	protected UndoBarcodeMessageDecoder(MessageDecoder<? extends Message> next){
		super(next,"UNDOBARCODE");
	}

	@Override
	public UndoBarcodeMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		Point coordinate = MessageDecoder.ghostCoordinateParameterToPoint(mes[2]);
		return new UndoBarcodeMessage(mes[0], coordinate);
	}

}
