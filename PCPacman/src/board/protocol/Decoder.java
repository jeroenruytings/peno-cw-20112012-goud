package board.protocol;

import java.text.ParseException;

public abstract class Decoder {
	private Decoder _next;
	public Decoder()
	{
		_next = new NullDecoder();
	}
	public Decoder(Decoder next)
	{
		_next=next;
	}
	public abstract boolean canDecode(String message);
	public abstract Command parse(String message) throws ParseException;
	public Decoder next()
	{
		if(_next == null)
			return new NullDecoder();
		return _next;
	}
	public void setNext(Decoder next)
	{
		_next=next;
	}
}
