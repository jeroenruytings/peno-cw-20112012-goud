package communicator.parser.decoder;

import communicator.parser.messages.NullMessage;

public class NullMessageDecoder extends MessageDecoder<NullMessage>
{

	
	public NullMessageDecoder()
	{
		super(null,"");
	}

	@Override
	public boolean canDecode(String message)
	{
		return true;
	}

	@Override
	public NullMessage parse(String message)
	{
		System.err.println("Het bericht: \"" + message + "\"" + " is weggesmeten");
		System.err.println("De fout zit waarschijnlijk in de corresponderende message decoder.");
		System.err.println("Kijk eens of de canDecode() methode klopt.");
		return new NullMessage();
	}

}
