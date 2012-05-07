package pacmansystem.ai.robot.simulatedRobo;

import java.io.InputStream;
import java.io.OutputStream;

import pacmansystem.ai.robot.simulatedRobo.stream.QueuedStream;


public class SimulationConnection
{
	private QueuedStream input;
	private QueuedStream output;
	public SimulationConnection()
	{
		input=new QueuedStream();
		output=new QueuedStream();
		
	}
	/**
	 * 
	 * @return
	 */
	public OutputStream getPcOut()
	{
		return  output.getOut();
		
	}
	OutputStream getRobotOut()
	{
		return input.getOut();
	}
	public InputStream getPCIN()
	{
		return input.getIn();
		
	}
	InputStream getRobotIN()
	{
		return output.getIn();
	}


}
