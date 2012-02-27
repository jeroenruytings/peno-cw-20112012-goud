package board;

import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import board.protocol.Command;
import board.protocol.ProtocolDecoder;

public class Simulator {
	private Collection<SimRobotData> _data;
	private Board	_globalBoard;
	private ProtocolDecoder _protocolDecoder;
	public Simulator()
	{
		_data	=	new ArrayList<SimRobotData>();
		_globalBoard	=	new Board(5,5);
		
	}
	public Iterable<SimRobotData> getSimRobots()
	{
		return new ArrayList<SimRobotData>(_data);
		
	}
	public void proces(String string) throws ParseException
	{
		procesCommand(parse(string));
	}
	
	private Command parse(String message) throws ParseException
	{
		return _protocolDecoder.parse(message);
	}
	private void procesCommand(Command command)
	{
		command.execute(this);
	}
}
