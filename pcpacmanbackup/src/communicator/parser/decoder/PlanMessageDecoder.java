package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.messages.Message;
import communicator.parser.messages.PlanMessage;


public class PlanMessageDecoder extends MessageDecoder<PlanMessage>
{

	protected PlanMessageDecoder(MessageDecoder<? extends Message> next)
	{
		super(next, "PLAN");
	}

	@Override
	public boolean canDecode(String message)
	{
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public PlanMessage parse(String message) throws ParseException
	{
		String msg = stripMessage(message);
		String[] mes = msg.split(" ");
		Point[] param = new Point[mes.length - 2];
		int x = 0;
		int y = 0;
		for(int i = 2; i <  mes.length; i++){
			x = Integer.parseInt(mes[i].split(",")[0]);
			y = Integer.parseInt(mes[i].split(",")[1]);
			param[i - 2] = new Point(x,y);
		}
		return new PlanMessage(mes[0], param);
	}

}
