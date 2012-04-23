package data.board.shortestpathfinder.dijkstra;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.board.Board;
import data.board.Panel;
import data.board.shortestpathfinder.ShortestPathFinderInterface;
import data.world.RobotData;


public class DijkstraFinder implements ShortestPathFinderInterface
{

	private Map<Point, Integer> _m1;
	private Map<Integer, Point> _m2;
	int v;
	private RobotData robot;

	public DijkstraFinder(RobotData robot)
	{
		this.robot=robot;
	}
	
	private RobotData getRobot(){
		return robot;
	}
	
	private EdgeWeightedDigraph make()
	{
		fillMaps(getRobot().getBoard());
		
		Board boardWithWallsForPacmanAndGhosts = new Board(getRobot().getBoard());
//		if (getRobot().getPacmanLastSighted() != null){
//			Panel pacman = new Panel(1,1,1,1);
//			boardWithWallsForPacmanAndGhosts.addForced(pacman, getRobot().getPacmanLastSighted());
//		}
		
		EdgeWeightedDigraph graph = new EdgeWeightedDigraph(v);
		for (Point p : boardWithWallsForPacmanAndGhosts.getPanels().keySet()) {
			for (Point point : boardWithWallsForPacmanAndGhosts.getSurrounding(p)) {
				if (!boardWithWallsForPacmanAndGhosts.wallBetween(p, point)) {
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
