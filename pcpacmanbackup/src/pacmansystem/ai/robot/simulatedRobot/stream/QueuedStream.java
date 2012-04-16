package pacmansystem.ai.robot.simulatedRobot.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class representing an input & outputstream.
 * 
 */
public class QueuedStream
{
	private OutputStream out_;
	private InputStream in_;
	private List<Integer> queue;
	private Object lock;
	/**
	 * Provides an input and an outputstream, everything that is presented to the outputstream can be read
	 * from the inputstream, the streams will never close.
	 */
	public QueuedStream()
	{
		lock = new Integer(1);
		queue = Collections.synchronizedList(new LinkedList<Integer>());
		in_ = new InputStream()
		{

			@Override
			public int read() throws IOException
			{
				while (!readingmode()) {
					try {
						synchronized (lock) {
							lock.wait();
						}
					} catch (InterruptedException e) {
					}
				}
				int i = queue.remove(0);
				return i;
			}

			private boolean readingmode()
			{
				return !queue.isEmpty();
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
