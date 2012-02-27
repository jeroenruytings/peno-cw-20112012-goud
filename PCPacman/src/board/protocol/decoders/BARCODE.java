package board.protocol.decoders;

import java.text.ParseException;

import board.CommandBarcode;
import board.protocol.Command;
import board.protocol.Decoder;

public class BARCODE extends Decoder {
	public BARCODE()
	{
		super("BARCODE");
	}
	@Override
	public boolean canDecode(String message) {
		if(!correctKey(message))
			return false;
		return true;
	}

	@Override
	public Command parse(String message) throws ParseException {
		// TODO Auto-generated method stub
		return new CommandBarcode()	;
	}


}
