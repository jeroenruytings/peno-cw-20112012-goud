package board;


import java.awt.Point;
import java.io.IOException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import board.protocol.Command;
import board.protocol.MQCommunicator;
import board.protocol.ProtocolDecoder;

public class Simulator {
	Thread	communicator;
	private Collection<SimRobotData> _data;
	private Board	_globalBoard;
	private ProtocolDecoder _protocolDecoder;
	
	public Simulator(String host) throws IOException
	{
		_data	=	new ArrayList<SimRobotData>();	
		communicator	= new Thread( new MQCommunicator(host, this));
		communicator.start();
		_globalBoard	=	new Board(5,5);
		
	}
	public void add(SimRobotData data)
	{
		_data.add(data);
	}
	
	public Iterable<SimRobotData> getSimRobots()
	{
		return new ArrayList<SimRobotData>(_data);
		
	}
	public void proces(String string) 
	{
		Command c;
		try{
		 c = parse(string);
		}catch(ParseException e)
		{
			return;
		}
		procesCommand(c);
	}
	
	private Command parse(String message) throws ParseException
	{
		return _protocolDecoder.parse(message);
	}
	private void procesCommand(Command command)
	{
		command.execute(this);
	}
	public Board getGlobalBoard()
	{
		return new Board(_globalBoard);
	}
	public Point getPacmanLocation() {

		return new Point(1,0);
	}
}
