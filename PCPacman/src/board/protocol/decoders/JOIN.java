package board.protocol.decoders;

import board.CommandJoin;
import board.protocol.Command;
import board.protocol.Decoder;

public class JOIN extends Decoder {

	public JOIN(Decoder next) {
		super(next);
	}

	public JOIN() {
		super();
	}

	@Override
	public boolean canDecode(String message) {
		return message.equals("JOIN");
	}

	@Override
	public Command parse(String message) {
		return new CommandJoin();
	}

}	
