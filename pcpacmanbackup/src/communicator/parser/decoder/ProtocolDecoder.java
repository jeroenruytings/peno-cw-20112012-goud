package communicator.parser.decoder;

import java.text.ParseException;

import communicator.parser.messages.Message;



public class ProtocolDecoder extends MessageDecoder<Message>
{
	private MessageDecoder<? extends Message> head;

	public ProtocolDecoder()
	{
		super(null, "decoder");
		head = new JoinMessageDecoder(
				new NameMessageDecoder
					(new PositionMessageDecoder
						(new DiscoverMessageDecoder
								(new BarcodeAtMessageDecoder
												(new PacmanMessageDecoder
														(new CapturedMessageDecoder
																(new PlanMessageDecoder
																		(new CancelPlanMessageDecoder
																				(new PingMessageDecoder
																						(new PongMessageDecoder
																								(new RebarcodeAtMessageDecoder
																										(new RediscoverMessageDecoder
																												(new RenameMessageDecoder
																														(new ReUndoBarcodeMessageDecoder
																																(new ShowmapMessageDecoder
																																		(new UndoBarcodeMessageDecoder
																																				(new NullMessageDecoder())))))))))))))))));
	}

	public Message parse(String string) throws ParseException
	{
		MessageDecoder<? extends Message> current = head;
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

	@Override
	public boolean canDecode(String message) {
		return true;
	}

}
