package pacmansystem.board;

import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Stack;

import pacmansystem.board.enums.Orientation;




public class ShortestPathFinder {
	private Board board;
	public ShortestPathFinder(Board board)
	{
		this.board=board;
	}
	private class Node implements Comparable<Node>{
		private int dist;
		private Node prev;
		private Point p;
		public Node(int d,Node prev,Point p) 
		{
			this.dist=d;
			this.prev=prev;
			this.p=p;
		}
		@Override
		public int compareTo(Node arg0) {
			if(this.dist>arg0.dist)
				return 1;
			if(this.dist<arg0.dist)
				return -1;
			return 0;
		}
		public Point getPoint() {
			return p;
		}
	}
	public Iterable<Point> shortestPath(Point start,Point end)
	{
		PriorityQueue<Node> pq = new PriorityQueue<ShortestPathFinder.Node>();
		pq.add(new Node(0,null,start));
		Node current=pq.poll();
		do{			
			for(Orientation d:Orientation.values()){
				if(!board.wallBetween(current.getPoint(), d))
						pq.add(new Node(current.dist+1,current,d.addTo(current.getPoint())));	
			}
			current = pq.poll();
		}while(!current.getPoint().equals(end));
		if(current.getPoint().equals(end))
			return makePath(current);
		return null;
	}
	private Iterable<Point> makePath(Node current) {
		Stack<Point> rv = new Stack<Point>();
		rv.push(current.p);
		while(current.prev!=null)
			rv.push((current = current.prev).p);
		return rv;
	}
}
