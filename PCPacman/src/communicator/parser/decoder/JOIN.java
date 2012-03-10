package communicator.parser.decoder;

import communicator.parser.Command;
import communicator.parser.Decoder;
import communicator.parser.NullDecoder;
import communicator.parser.command.CommandJoin;


public class JOIN extends Decoder
{

	public JOIN()
	{
		super(new NAME
				(new POSITION
						(new DISCOVER
								(new BARCODE
										(new BARCODEAT
												(new PACMAN
														(new CAPTURED
																(new PLAN
																		(new CANCELPLAN
																				(new PING
																						(new PONG
																								(new NullDecoder())))))))))))
				, "JOIN");
	}

	@Override
	public boolean canDecode(String message)
	{
		return message.equals("JOIN\n");
	}

	@Override
	public Command parse(String message)
	{
		return new CommandJoin();
	}

}
