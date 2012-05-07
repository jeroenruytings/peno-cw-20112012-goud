package pacmansystem.ai.robot.simulatedRobo.stream;

import static org.junit.Assert.assertTrue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

public class _QueuedStreamTest
{
	QueuedStream stream;

	@Before
	public void before()
	{
		stream = new QueuedStream();
	}

	@Test
	public void simpleints() throws IOException
	{
		int num = 255;
		for (int i = 0; i < num; i++)
			stream.getOut().write(i);
		for (int i = 0; i < num; i++)
			assertTrue(stream.getIn().read() == i);
	}

	public final static int terminator = -1;

	class WritingRunnable implements Runnable
	{
		private OutputStream out;

		public WritingRunnable(OutputStream out)
		{
			this.out = out;
		}

		@Override
		public void run()
		{
			for (int i = 0; i < 254; i++)
				try {
					out.write(i);
				} catch (Exception e) {

				}
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	class ReadRunnable implements Runnable
	{
		private InputStream stream;
		private Object lock = new String();
		private boolean finished;

		public ReadRunnable(InputStream in)
		{
			this.stream = in;
		}

		@Override
		public void run()
		{
			int i = 0;
			int val = 0;
			while (val != terminator) {
				try {
					val = stream.read();
					if (val != i && val != terminator)
						throw new Error("abra");
				} catch (Exception e) {
					throw new Error("xxx");
				}
				i++;
			}
			finished = true;
			synchronized (lock) {
				lock.notify();

			}
		}

		public void waitForAll()
		{

			while (!finished)
				try {
					synchronized (lock) {
						lock.wait();
					}
				} catch (InterruptedException e) {

				}

		}

	}

	@Test
	public void ManyThings()
	{
		ReadRunnable r = new ReadRunnable(stream.getIn());
		Thread threadOne = new Thread(new WritingRunnable(stream.getOut()));
		Thread threadTwo = new Thread(r);
		threadOne.start();
		threadTwo.start();
		r.waitForAll();
		assertTrue(r.finished);
	}

	class pusher
	{
		public pusher(final InputStream in, final OutputStream out)
		{
			new Thread(new Runnable()
			{

				@Override
				public void run()
				{
					while (true)
						try {
							out.write(in.read());
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}).start();

		}

	}

	@Test
	public void doubleTime()
	{
		QueuedStream s1 = new QueuedStream();
		QueuedStream s2 = new QueuedStream();
		new pusher(s1.getIn(), s2.getOut());
		int magicnumber = 1;
		try {
			s1.getOut().write(magicnumber);
			assertTrue(s2.getIn().read() == magicnumber);
		} catch (IOException e) {
		}
	}
	@Test
	public void dataOutputInput()
	{
		int magicNumber=10;
		DataOutputStream out = new DataOutputStream(stream.getOut());
		DataInputStream in = new DataInputStream(stream.getIn());
		try {
			out.writeInt(magicNumber);
			out.flush();
		} catch (IOException e) {
		}
		try {
			System.out.println(in.readInt());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void dataStreams() throws IOException
	{
		DataInputStream in = new DataInputStream(stream.getIn());
		DataOutputStream out = new DataOutputStream(stream.getOut());
		out.writeByte(-1);
		System.out.println("addd"+(byte)(in.readByte()&0x000000ff));
		
	}
}
