package communicator.parser;

import java.text.ParseException;

import communicator.parser.decoder.JOIN;
import communicator.parser.messages.Message;



public class ProtocolDecoder
{
	private Decoder head;

	public ProtocolDecoder()
	{
		head = new JOIN();
	}

	public Message parse(String string) throws ParseException
	{
		Decoder current = head;
		while (!current.canDecode(string))
			current = current.next();
		try{
			return current.parse(string);
		}
		catch(NumberFormatException e){
			System.err.println("\n" + string + "\n");
			e.printStackTrace();
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.err.println("\n" + string + "\n");
			e.printStackTrace();
		}
		// If an error occurred, then quit the program.
		System.exit(0);
		return null;
		
	}

}
