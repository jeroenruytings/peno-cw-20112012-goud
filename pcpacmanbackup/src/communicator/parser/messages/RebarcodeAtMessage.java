package communicator.parser.messages;

import java.awt.Point;

public class RebarcodeAtMessage extends BarcodeAtMessage {

	public RebarcodeAtMessage(String name, Point coordinate, int barcode,
			int direction) {
		super(name, coordinate, barcode, direction);
	}
	
	public String getKeyword(){
		return "REBARCODEAT";
	}

}
