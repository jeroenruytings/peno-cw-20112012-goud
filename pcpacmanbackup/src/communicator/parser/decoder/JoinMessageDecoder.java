package communicator.parser.decoder;

import communicator.parser.messages.JoinMessage;
import communicator.parser.messages.Message;


public class JoinMessageDecoder extends MessageDecoder<JoinMessage>
{

	protected JoinMessageDecoder(MessageDecoder<? extends Message> next)
	{
		super(next, "JOIN");
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
