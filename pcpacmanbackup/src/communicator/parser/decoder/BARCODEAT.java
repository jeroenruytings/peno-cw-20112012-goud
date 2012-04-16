package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.Decoder;
import communicator.parser.messages.BarcodeAtMessage;


public class BARCODEAT extends Decoder {

	protected BARCODEAT(Decoder next){
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
		String[] coord = mes[2].split(",");
		Point coordinate = new Point(Integer.parseInt(coord[0]),Integer.parseInt(coord[1]));
		return new BarcodeAtMessage(mes[0], coordinate,Integer.parseInt(mes[3]),
				Integer.parseInt(mes[4]));
	}

}
