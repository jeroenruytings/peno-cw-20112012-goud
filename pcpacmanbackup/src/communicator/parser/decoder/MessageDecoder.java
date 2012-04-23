package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;
import java.util.Arrays;

import communicator.parser.messages.Message;

public abstract class MessageDecoder<M extends Message>
{
	protected String _key;
	private MessageDecoder<? extends Message> _next;

//	public Decoder(String key)
//	{
//		this._key = key;
//		_next = new NullDecoder();
//	}

	protected MessageDecoder(MessageDecoder<? extends Message> next, String key)
	{
		this._key = key;
		_next = next;
	}

	public boolean canDecode(String message){
		if (!correctKey(message))
			return false;
		return true;
	}

	public abstract M parse(String message) throws ParseException;
	
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

	public MessageDecoder<? extends Message> next()
	{
		if (_next == null)
			return new NullMessageDecoder();
		return _next;
	}

	public void setNext(MessageDecoder<? extends Message> next)
	{
		_next = next;
	}

	protected boolean correctKey(String message)
	{
		MessageDecoder.stripMessage(message);
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
	
	public static Point ghostCoordinateParameterToPoint(String parameter){
		Point coordinate = null;
		try{
			String[] coord = parameter.split(",");
			coordinate = new Point(Integer.parseInt(coord[0]),Integer.parseInt(coord[1]));
		}
		catch(Exception e)
		{
			new IllegalArgumentException("De volgende string werd doorgegeven om er een coordinaat van te maken" + parameter);
		}
		return coordinate;
		
	}
	
	public static void main(String[] args){
		String s = "Goud0.8831854161306725 NAME 1.0\n";
		s = MessageDecoder.stripMessage(s);
		System.out.print(s);
		System.out.print(Arrays.toString(s.split(" ")));
		//Scanner scr = new Scanner(System.in);
		//String message = "\n";
		//System.out.println(message.replaceAll("\\n", "").equals("\n"));
		//System.out.println(message);
	}
}
