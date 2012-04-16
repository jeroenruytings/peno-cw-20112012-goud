package communicator.parser.decoder;

import communicator.parser.Decoder;
import communicator.parser.NullDecoder;
import communicator.parser.messages.JoinMessage;


public class JOIN extends Decoder
{

	public JOIN()
	{
		super(new NAME
				(new POSITION
						(new DISCOVER
								(new BARCODEAT
												(new PACMAN
														(new CAPTURED
																(new PLAN
																		(new CANCELPLAN
																				(new PING
																						(new PONG
																								(new NullDecoder()))))))))))
				, "JOIN");
	}

	@Override
	public boolean canDecode(String message)
	{
		return message.equals("JOIN\n") || message.equals("JOIN");
	}

	@Override
	public JoinMessage parse(String message)
	{
		return new JoinMessage();
	}

}
