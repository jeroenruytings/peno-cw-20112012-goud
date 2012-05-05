package pacmansystem.ai.robot.simulatedRobot.point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class SegmentIterator implements Iterator<Segment>
{
	private List<Pointf> points;
	private Queue<Segment> segments = new LinkedList<Segment>();
	public SegmentIterator(List<Pointf> points)
	{
		this.points = new ArrayList<Pointf>(points);
		Pointf current =null;
		for(Pointf point:points)
		{
			if(current == null)
			{
				current = point;
				continue;
			}
			segments.add(new Segment(current, point));
			current=point;
		}
		segments.add(new Segment(points.get(points.size()-1), points.get(0)));
	}
	public SegmentIterator(Pointf... points2)
	{
		this(Arrays.asList(points2));
	}
	@Override
	public boolean hasNext()
	{
		
		return !segments.isEmpty();
	}

	@Override
	public Segment next()
	{
		return segments.poll();
	}

	@Override
	public void remove()
	{
	}
	public Iterable<Segment> getIterable()
	{
		return new Iterable<Segment>()
		{
			
			@Override
			public Iterator<Segment> iterator()
			{
			
				return new SegmentIterator(points);
			}
		};
	}
}
