package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.ReUndoBarcodeMessage;

public class ReUndoBarcodeMessageDecoder extends MessageDecoder<ReUndoBarcodeMessage> {

	protected ReUndoBarcodeMessageDecoder(
			MessageDecoder<? extends Message> next) {
		super(next, "REUNDOBARCODE");
	}

	@Override
	public ReUndoBarcodeMessage parse(String message) throws ParseException {
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		Point coordinate = MessageDecoder.ghostCoordinateParameterToPoint(mes[2]);
		return new ReUndoBarcodeMessage(mes[0], coordinate);
	}

}
