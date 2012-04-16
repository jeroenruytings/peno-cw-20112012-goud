package communicator.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import communicator.parser.Decoder;
import communicator.parser.messages.PlanMessage;


public class PLAN extends Decoder
{

	protected PLAN(Decoder next)
	{
		super(next, "PLAN");
	}

	@Override
	public boolean canDecode(String message)
	{
		return false;
//		if (!correctKey(message))
//			return false;
//		return true;
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
			try{
			x = Integer.parseInt(mes[i].split(",")[0]);
			y = Integer.parseInt(mes[i].split(",")[1]);
			} catch(NumberFormatException e)
			{
				System.out.println(message);
				System.out.println(mes[i].split(",")[0]);
				System.out.println(mes[i].split(",")[1]);
			}
			param[i - 2] = new Point(x,y);
		}
		return new PlanMessage(mes[0], param);
	}

}
