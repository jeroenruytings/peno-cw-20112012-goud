package data.board.shortestpathfinder.dijkstra;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.board.Board;
import data.board.shortestpathfinder.ShortestPathFinderInterface;


public class DijkstraFinder implements ShortestPathFinderInterface
{

	private Map<Point, Integer> _m1;
	private Map<Integer, Point> _m2;
	int v;
	private Board board;

	//TODO: Misschien is het geen slecht idee om aan dijkstra finder enkel een bord te geven.
	//		Op die manier is het niet de verantwoordelijkheid van dijkstra-finder om te te kiezen tussen merged/niet-gemerged
	public DijkstraFinder(Board board)
	{
		this.board=board;
	}
	private Board getBoard(){
		return board;
	}
	
	private EdgeWeightedDigraph make()
	{
		fillMaps(getBoard());
		
		Board boardWithWallsForPacmanAndGhosts = new Board(getBoard());
//		if (getRobot().getPacmanLastSighted() != null){
//			Panel pacman = new Panel(1,1,1,1);
//			boardWithWallsForPacmanAndGhosts.addForced(pacman, getRobot().getPacmanLastSighted());
//		}
		
		EdgeWeightedDigraph graph = createGraphFromBoard(boardWithWallsForPacmanAndGhosts);

		return graph;
	}

	/**
	 * Create a graph from the given board. Each panel is a vertex, if there is no wall between two neighboring panels, an edge will be created.
	 * @param 	board
	 * 				The board to create the graph from.
	 * @return	The graph create from the given board.
	 */
	private EdgeWeightedDigraph createGraphFromBoard(Board board) {
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
			throw new IllegalArgumentException("Hoe moet ik weten of 'null' op het bord ligt?");
		Integer i= _m1.get(p);
		if(i==null)
			throw new IllegalStateException("Je vraag naar een punt dat niet op het bord ligt");
		return i;
	}

	public Point delinearize(int v)
	{
		return _m2.get(v);
	}

	@Override
	public List<Point> shortestPath(Point one, Point two) throws PathNotPossibleException
	{
		ArrayList<Point> rv = new ArrayList<Point>();
		EdgeWeightedDigraph graph = make();
		DijkstraSP f = new DijkstraSP(graph, linearize(one));
		Iterable<DirectedEdge> edges = f.pathTo(linearize(two));
		if (edges == null)
			throw new PathNotPossibleException();
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
