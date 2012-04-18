package communicator.parser.messages;

import java.awt.Point;

public class ReUndoBarcodeMessage extends UndoBarcodeMessage {

	public ReUndoBarcodeMessage(String name, Point coordinate) {
		super(name, coordinate);
	}
	
	@Override
	public String getKeyword(){
		return "REUNDOBARCODE";
	}

}
