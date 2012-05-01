package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.messages.BarcodeAtMessage;
import communicator.parser.messages.Message;


public class BarcodeAtMessageDecoder extends MessageDecoder<BarcodeAtMessage> {

	protected BarcodeAtMessageDecoder(MessageDecoder<? extends Message> next){
		super(next,"BARCODEAT");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public BarcodeAtMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		Point coordinate = MessageDecoder.ghostCoordinateParameterToPoint(mes[2]);
		return new BarcodeAtMessage(mes[0], coordinate,Integer.parseInt(mes[3]),
				Integer.parseInt(mes[4]));
	}

}
