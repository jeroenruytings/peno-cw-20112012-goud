package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.RebarcodeAtMessage;


public class RebarcodeAtMessageDecoder extends MessageDecoder<RebarcodeAtMessage> {

	protected RebarcodeAtMessageDecoder(MessageDecoder<? extends Message> next) {
		super(next, "REBARCODEAT");
	}

	@Override
	public RebarcodeAtMessage parse(String message) throws ParseException {
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		Point coordinate = MessageDecoder.ghostCoordinateParameterToPoint(mes[2]);
		return new RebarcodeAtMessage(mes[0], coordinate,Integer.parseInt(mes[3]),
				Integer.parseInt(mes[4]));
	}

}
