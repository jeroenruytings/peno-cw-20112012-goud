package data.help;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Filter<T>
{
	public Object[] _objs;
	public Filter(Object...obs)
	{
		_objs=obs;
	}
	public static <T> Collection<T> filter(Collection<T> collection,Filter<T> filter)
	{
		ArrayList<T> rv = new ArrayList<T>();
		for(T t:collection)
			if(filter.accepts(t))
				rv.add(t);
		return rv;
		
	}
	public abstract boolean accepts(T arg);
	
}
