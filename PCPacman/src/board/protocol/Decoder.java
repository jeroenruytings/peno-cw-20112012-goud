package board.protocol;

import java.text.ParseException;

public abstract class Decoder {
	protected String _key;
	private Decoder _next;
	public Decoder(String key)
	{
		this._key=key;
		_next = new NullDecoder();
	}
	protected Decoder(Decoder next,String key)
	{
		this._key = key;
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
	protected boolean correctKey(String message) {
		String[] mes = message.split(" ");
		if(!mes[1].equals("_key"))
			return false;
		return true;
	}
}
