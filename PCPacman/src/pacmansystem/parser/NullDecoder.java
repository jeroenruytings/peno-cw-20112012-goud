package pacmansystem.parser;

import pacmansystem.parser.command.NullCommand;

public class NullDecoder extends Decoder {

	public NullDecoder(Decoder next) {
		super(null);
	}

	public NullDecoder() {
		super(null);
	}

	@Override
	public boolean canDecode(String message) {
		return true;
	}

	@Override
	public Command parse(String message) {
		return new NullCommand();
	}

}
