package util.board.shortestpathfinder.dijkstra;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.board.Board;
import util.board.shortestpathfinder.ShortestPathFinder;

public class DijkstraFinder extends ShortestPathFinder
{

	private Map<Point, Integer> _m1;
	private Map<Integer, Point> _m2;
	int v;

	public DijkstraFinder(Board board)
	{
		super(board);

	}

	private EdgeWeightedDigraph make()
	{
		fillMaps(board);
		EdgeWeightedDigraph graph = new EdgeWeightedDigraph(v);
		for (Point p : board.getPanels().keySet()) {
			for (Point point : board.getSurrounding(p)) {
				if (!board.wallBetween(p, point)) {
					graph.addEdge(new DirectedEdge(linearize(p),
							linearize(point), 1));
					graph.addEdge(new DirectedEdge(linearize(point),
							linearize(p), 1));

				}
			}
		}

		return graph;
	}

	private void fillMaps(Board board)
	{
		_m2 = new HashMap<Integer, Point>();
		_m1 = new HashMap<Point, Integer>();
		v = 0;
		for (Point p : board.getPanels().keySet()) {
			for(Point point:board.getSurrounding(p))
			{
				if(_m1.containsKey(point))
					continue;
				_m1.put(point, v);
				_m2.put(v, point);
				v++;
			}
			_m1.put(p, v);
			_m2.put(v, p);
			v++;
		}
	}

	public int linearize(Point p)
	{
		if(p==null||_m1==null)
			System.out.println("fuc,k dit");
		Integer i= _m1.get(p);
		if(i==null)
			System.out.println("asdf");
		return i;
	}

	public Point delinearize(int v)
	{
		return _m2.get(v);
	}

	@Override
	public Iterable<Point> shortestPath(Point one, Point two)
	{
		ArrayList<Point> rv = new ArrayList<Point>();
		EdgeWeightedDigraph graph = make();
		DijkstraSP f = new DijkstraSP(graph, linearize(one));
		Iterable<DirectedEdge> edges = f.pathTo(linearize(two));
		rv.add(delinearize(edges.iterator().next().from()));
		for (DirectedEdge e : edges)
			rv.add(delinearize(e.to()));
		return rv;
	}

}