package pacmansystem.parser.decoder;

import java.awt.Point;
import java.text.ParseException;

import pacmansystem.parser.Command;
import pacmansystem.parser.Decoder;
import pacmansystem.parser.command.CommandPlan;

public class PLAN extends Decoder
{

	protected PLAN(Decoder next)
	{
		super(next, "PLAN");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDecode(String message)
	{
		
		if (!correctKey(message))
			return false;
		return true;
	}

	@Override
	public Command parse(String message) throws ParseException
	{
		String[] mes = message.split(" ");
		Point[] param = new Point[mes.length - 2];
		int x;
		int y;
		for(int i = 1; i <  mes.length; i++){
			x = Integer.parseInt(mes[i].split(",")[0]);
			y = Integer.parseInt(mes[i].split(",")[1]);
			param[i - 2] = new Point(x,y);
		}
		return new CommandPlan(mes[0], param);
	}

}
