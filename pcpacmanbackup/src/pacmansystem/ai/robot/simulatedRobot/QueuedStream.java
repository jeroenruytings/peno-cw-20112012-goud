package pacmansystem.ai.robot.simulatedRobot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class QueuedStream
{
	private OutputStream out_;
	private InputStream in_;
	private Queue<Integer> queue;
	private Object lock;

	public QueuedStream()
	{
		lock = new Integer(1);
		queue = new LinkedList<Integer>();
		in_ = new InputStream()
		{

			boolean readingmode = false;

			@Override
			public int read() throws IOException
			{
				if (!readingmode)
					try {
						synchronized (lock) {

							lock.wait();
						}
						readingmode = true;
					} catch (InterruptedException e) {
					}
				if (queue.size() == 1)
					readingmode = false;
				return queue.poll();
			}

		};
		out_ = new OutputStream()
		{

			@Override
			public void write(int b) throws IOException
			{
				queue.add(b);
				synchronized (lock) {

					lock.notify();
				}
			}
		};
	}

	public OutputStream getOut()
	{
		return out_;
	}

	public InputStream getIn()
	{
		return in_;
	}

}
