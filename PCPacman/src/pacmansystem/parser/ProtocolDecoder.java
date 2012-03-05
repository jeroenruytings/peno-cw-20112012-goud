package pacmansystem.parser;

import java.text.ParseException;

import pacmansystem.parser.decoder.JOIN;

public class ProtocolDecoder
{
	private Decoder head;

	public ProtocolDecoder()
	{
		head = new JOIN();
	}

	public Command parse(String string) throws ParseException
	{
		Decoder current = head;
		while (!current.canDecode(string))
			current = current.next();
		return current.parse(string);
	}

}
