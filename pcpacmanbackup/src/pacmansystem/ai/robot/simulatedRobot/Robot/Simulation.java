package pacmansystem.ai.robot.simulatedRobot.Robot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Simulation implements Runnable
{
	private final RobotCommunicator comm;
	public Simulation(InputStream in,OutputStream out)
	{
		comm = new RobotCommunicator(new DataInputStream(in), new DataOutputStream(out));
	}
	@Override
	public void run()
	{
		SensorListener listener = new SensorListener(comm);
		new Thread(listener).start();
		CommandoListener commandoListner = new CommandoListener(listener, comm);
		commandoListner.run();
		
	}

}
