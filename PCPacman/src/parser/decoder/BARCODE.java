package parser.decoder;

import java.text.ParseException;

import parser.Command;
import parser.Decoder;
import parser.command.CommandBarcode;


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
		String[] mes = message.split(" ");
		return new CommandBarcode(mes[0],Integer.parseInt(mes[2]),Integer.parseInt(mes[3]));
	}


}
