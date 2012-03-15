package util.board.shortestpathfinder.dijkstra;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.board.Board;
import util.board.shortestpathfinder.ShortestPathFinderInterface;

public class DijkstraFinder implements ShortestPathFinderInterface
{

	private Map<Point, Integer> _m1;
	private Map<Integer, Point> _m2;
	int v;
	private Board board;

	public DijkstraFinder(Board board)
	{
		this.board=board;

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
			if (! _m1.containsKey(p)){
				_m1.put(p, v);
				_m2.put(v, p);
				v++;
				}
			for(Point point:board.getSurrounding(p))
			{
				if(_m1.containsKey(point))
					continue;
				_m1.put(point, v);
				_m2.put(v, point);
				v++;
			}
			
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
	public List<Point> shortestPath(Point one, Point two)
	{
		ArrayList<Point> rv = new ArrayList<Point>();
		EdgeWeightedDigraph graph = make();
		DijkstraSP f = new DijkstraSP(graph, linearize(one));
		Iterable<DirectedEdge> edges = f.pathTo(linearize(two));
		for (DirectedEdge e : edges)
			rv.add(delinearize(e.to()));
		rv.add(one);
		return invert(rv);
	}

	private List<Point> invert(ArrayList<Point> arg)
	{
		ArrayList<Point> rv= new ArrayList<Point>();
		for(int i = arg.size()-1;i>=0;i--)
			rv.add(arg.get(i));
		return rv;
	}

}
