package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.ShowmapMessage;

public class ShowmapMessageDecoder extends MessageDecoder<ShowmapMessage> {

	protected ShowmapMessageDecoder(MessageDecoder<? extends Message> next) {
		super(next, "SHOWMAP");
	}

	@Override
	public ShowmapMessage parse(String message) throws ParseException {
		String[] mes = stripMessage(message).split(" ");
		return new ShowmapMessage(mes[0], mes[2]);
	}
	
	

	
	
}
