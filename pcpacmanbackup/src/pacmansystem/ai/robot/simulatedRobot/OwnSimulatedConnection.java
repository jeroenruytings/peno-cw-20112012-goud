package pacmansystem.ai.robot.simulatedRobot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OwnSimulatedConnection
{

	private QueuedStream input;
	private QueuedStream output;
	public OwnSimulatedConnection(QueuedStream in,QueuedStream out)
	{
		input=in;
		output=out;
		
	}
	public OutputStream getOut()
	{
		return  output.getOut();
		
	}
	public InputStream getIn()
	{
		return input.getIn();
		
	}

}
