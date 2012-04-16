package communicator.parser;

import java.text.ParseException;
import java.util.Arrays;

import communicator.parser.messages.Message;

public abstract class Decoder
{
	protected String _key;
	private Decoder _next;

//	public Decoder(String key)
//	{
//		this._key = key;
//		_next = new NullDecoder();
//	}

	protected Decoder(Decoder next, String key)
	{
		this._key = key;
		_next = next;
	}

	public abstract boolean canDecode(String message);

	public abstract Message parse(String message) throws ParseException;
	
	/**
	 * This message strips messages to the right format for the parse method.
	 * @param 	message
	 * 				The message to strip.
	 * @return
	 * 			The stripped message.
	 */
	public static String stripMessage(String message){
		return message.replace("\\n", "").replace("\n", "");
		//return message.substring(0, message.length() - 1).replace("\\", "");
		
	}

	public Decoder next()
	{
		if (_next == null)
			return new NullDecoder();
		return _next;
	}

	public void setNext(Decoder next)
	{
		_next = next;
	}

	protected boolean correctKey(String message)
	{
		Decoder.stripMessage(message);
		String[] mes = message.split(" ");
		try{
		if (!mes[1].equals(_key))
			return false;
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println(message);
		}
		return true;
	}
	
	public static void main(String[] args){
		String s = "Goud0.8831854161306725 NAME 1.0\n";
		s = Decoder.stripMessage(s);
		System.out.print(s);
		System.out.print(Arrays.toString(s.split(" ")));
		//Scanner scr = new Scanner(System.in);
		//String message = "\n";
		//System.out.println(message.replaceAll("\\n", "").equals("\n"));
		//System.out.println(message);
	}
}
