package pacmansystem.ai.robot.simulatedRobot;

import java.io.InputStream;
import java.io.OutputStream;

import pacmansystem.ai.robot.simulatedRobot.stream.QueuedStream;

public class OwnSimulatedConnection
{

	private QueuedStream input;
	private QueuedStream output;
	public OwnSimulatedConnection(QueuedStream in,QueuedStream out)
	{
		input=in;
		output=out;
		
	}
	/**
	 * 
	 * @return
	 */
	public OutputStream getPcOut()
	{
		return  output.getOut();
		
	}
	public OutputStream getRobotOut()
	{
		return input.getOut();
	}
	public InputStream getPCIN()
	{
		return input.getIn();
		
	}
	public InputStream getRobotIN()
	{
		return output.getIn();
	}

}
